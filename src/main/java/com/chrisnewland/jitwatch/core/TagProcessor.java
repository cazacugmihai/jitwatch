/*
 * Copyright (c) 2013, 2014 Chris Newland.
 * Licensed under https://github.com/AdoptOpenJDK/jitwatch/blob/master/LICENSE-BSD
 * Instructions: https://github.com/AdoptOpenJDK/jitwatch/wiki
 */
package com.chrisnewland.jitwatch.core;

import java.util.Map;

import com.chrisnewland.jitwatch.model.Tag;
import com.chrisnewland.jitwatch.model.Task;
import com.chrisnewland.jitwatch.util.StringUtil;
import static com.chrisnewland.jitwatch.core.JITWatchConstants.*;

public class TagProcessor
{
	// feed it lines until it completes a tag
	private Tag currentTag;
	private Tag topTag = null;

	public Tag processLine(String line)
	{
		Tag result = null;

		if (line != null && line.length() > 3)
		{
			if (line.charAt(0) == C_OPEN_BRACKET)
			{
				// closing tag
				if (line.charAt(1) == C_SLASH)
				{
					String closeName = line.substring(2, line.length() - 1);

					if (closeName.equals(currentTag.getName()))
					{
						if (currentTag.getParent() == null)
						{
							result = currentTag;
						}
						else
						{
							currentTag = currentTag.getParent();
						}
					}
				}
				else
				{
					int indexEndName = line.indexOf(C_SPACE);

					if (indexEndName == -1)
					{
						indexEndName = line.indexOf(C_CLOSE_BRACKET);
					}

					String name = line.substring(1, indexEndName);

					String remainder = line.substring(indexEndName);

					Map<String, String> attrs = StringUtil.getLineAttributes(remainder);

					boolean selfClosing = (line.charAt(line.length() - 2) == C_SLASH);

					Tag t;

					if (JITWatchConstants.TAG_TASK.equals(name))
					{
						t = new Task(name, attrs, selfClosing);
					}
					else
					{
						t = new Tag(name, attrs, selfClosing);
					}

					if (currentTag == null)
					{
						// new tag at top level
						currentTag = t;
						topTag = t;
					}
					else
					{
						currentTag.addChild(t);
					}

					if (topTag instanceof Task)
					{
						switch (name)
						{
						case JITWatchConstants.TAG_TYPE:
							((Task) topTag).addDictionaryType(attrs.get(JITWatchConstants.ATTR_ID), t);
							break;

						case JITWatchConstants.TAG_METHOD:
							((Task) topTag).addDictionaryMethod(attrs.get(JITWatchConstants.ATTR_ID), t);
							break;

						case JITWatchConstants.TAG_KLASS:
							((Task) topTag).addDictionaryKlass(attrs.get(JITWatchConstants.ATTR_ID), t);
							break;
						}
					}

					if (selfClosing)
					{
						if (name.equals(currentTag.getName()))
						{
							if (currentTag.getParent() == null)
							{
								result = currentTag;
							}
						}
					}
					else
					{
						// not closed
						currentTag = t;
					}
				}
			}
		}

		if (result != null)
		{
			currentTag = null;
			topTag = null;
		}

		return result;
	}

}