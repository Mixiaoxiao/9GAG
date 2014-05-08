package com.mixiaoxiao.ninegag.fragment;

import com.mixiaoxiao.ninegag.manager.FeedsManager;


public class GagFragmentHot extends GagFragment {
	
	@Override
	protected FeedsManager getFeedsManager() {
		// TODO Auto-generated method stub
		return new FeedsManager(FeedsManager.TYPE_HOT, getActivity());
	}
}
