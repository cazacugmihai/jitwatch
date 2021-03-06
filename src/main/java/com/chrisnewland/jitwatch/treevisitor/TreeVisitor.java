package com.chrisnewland.jitwatch.treevisitor;

import java.util.List;

import com.chrisnewland.jitwatch.model.IMetaMember;
import com.chrisnewland.jitwatch.model.IReadOnlyJITDataModel;
import com.chrisnewland.jitwatch.model.MetaClass;
import com.chrisnewland.jitwatch.model.MetaPackage;

public class TreeVisitor
{
	public static void walkTree(IReadOnlyJITDataModel model, ITreeVisitable visitable)
	{
		visitable.reset();
		
		List<MetaPackage> roots = model.getPackageManager().getRootPackages();

		for (MetaPackage mp : roots)
		{
			walkPackage(mp, visitable);
		}
	}

	private static void walkPackage(MetaPackage mp, ITreeVisitable visitable)
	{
		List<MetaPackage> childPackages = mp.getChildPackages();

		for (MetaPackage childPackage : childPackages)
		{
			walkPackage(childPackage, visitable);
		}

		List<MetaClass> packageClasses = mp.getPackageClasses();

		for (MetaClass mc : packageClasses)
		{
			for (IMetaMember mm : mc.getMetaMembers())
			{
				visitable.visit(mm);
			}
		}
	}
}
