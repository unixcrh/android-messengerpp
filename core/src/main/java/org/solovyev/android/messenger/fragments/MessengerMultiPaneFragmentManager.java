package org.solovyev.android.messenger.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import org.solovyev.android.fragments.MultiPaneFragmentDef;
import org.solovyev.android.fragments.MultiPaneFragmentManager;
import org.solovyev.android.fragments.ReflectionFragmentBuilder;
import org.solovyev.android.messenger.MessengerFragmentActivity;
import org.solovyev.android.messenger.core.R;
import org.solovyev.android.messenger.messages.MessengerEmptyFragment;
import org.solovyev.common.Builder;
import org.solovyev.common.JPredicate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;
import static org.solovyev.android.messenger.fragments.MessengerPrimaryFragment.*;

public class MessengerMultiPaneFragmentManager extends MultiPaneFragmentManager {

	public static final List<MessengerPrimaryFragment> tabFragments;

	static {
		final List<MessengerPrimaryFragment> mutableTabFragments = new ArrayList<MessengerPrimaryFragment>();
		mutableTabFragments.add(contacts);
		mutableTabFragments.add(messages);
		mutableTabFragments.add(accounts);
		mutableTabFragments.add(settings);
		tabFragments = unmodifiableList(mutableTabFragments);
	}

	public MessengerMultiPaneFragmentManager(@Nonnull MessengerFragmentActivity activity) {
		super(activity, R.id.content_first_pane, MessengerEmptyFragment.class, MessengerEmptyFragment.FRAGMENT_TAG, R.anim.mpp_fragment_fade_in, R.anim.mpp_fragment_fade_out);
	}

	@Nonnull
	@Override
	public MessengerFragmentActivity getActivity() {
		return (MessengerFragmentActivity) super.getActivity();
	}

	public void setSecondFragment(@Nonnull Class<? extends Fragment> fragmentClass,
								  @Nullable Bundle fragmentArgs,
								  @Nullable JPredicate<Fragment> reuseCondition,
								  @Nonnull String fragmentTag,
								  boolean addToBackStack) {
		setFragment(R.id.content_second_pane, MultiPaneFragmentDef.newInstance(fragmentTag, addToBackStack, ReflectionFragmentBuilder.forClass(getActivity(), fragmentClass, fragmentArgs), reuseCondition));
	}

	public void setSecondFragment(@Nonnull Builder<Fragment> fragmentBuilder,
								  @Nullable JPredicate<Fragment> reuseCondition,
								  @Nonnull String fragmentTag) {
		setFragment(R.id.content_second_pane, MultiPaneFragmentDef.newInstance(fragmentTag, false, fragmentBuilder, reuseCondition));
	}

	public void emptifySecondFragment() {
		emptifyFragmentPane(R.id.content_second_pane);
	}

	public void setThirdFragment(@Nonnull Class<? extends Fragment> fragmentClass,
								 @Nullable Bundle fragmentArgs,
								 @Nullable JPredicate<Fragment> reuseCondition,
								 @Nonnull String fragmentTag) {
		setFragment(R.id.content_third_pane, MultiPaneFragmentDef.newInstance(fragmentTag, false, ReflectionFragmentBuilder.forClass(getActivity(), fragmentClass, fragmentArgs), reuseCondition));
	}

	public void setThirdFragment(@Nonnull Builder<Fragment> fragmentBuilder,
								 @Nullable JPredicate<Fragment> reuseCondition,
								 @Nonnull String fragmentTag) {
		setFragment(R.id.content_third_pane, MultiPaneFragmentDef.newInstance(fragmentTag, false, fragmentBuilder, reuseCondition));
	}

	public void emptifyThirdFragment() {
		emptifyFragmentPane(R.id.content_third_pane);
	}

	public void setSecondOrMainFragment(Class<? extends Fragment> fragmentClass, Bundle fragmentArgs, String fragmentTag, boolean addToBackStack) {
		if (getActivity().isDualPane()) {
			setSecondFragment(fragmentClass, fragmentArgs, null, fragmentTag, addToBackStack);
		} else {
			setMainFragment(fragmentClass, fragmentArgs, null, fragmentTag, addToBackStack);
		}
	}
}
