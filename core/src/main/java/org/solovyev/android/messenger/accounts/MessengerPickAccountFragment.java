package org.solovyev.android.messenger.accounts;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.solovyev.android.messenger.MessengerListItemAdapter;
import org.solovyev.android.messenger.api.MessengerAsyncTask;

import com.google.common.base.Function;

import static com.google.common.collect.Iterables.toArray;
import static com.google.common.collect.Iterables.transform;
import static org.solovyev.android.messenger.accounts.AccountUiEventType.account_picked;
import static org.solovyev.android.messenger.accounts.AccountUiEventType.account_view_requested;
import static org.solovyev.common.collections.Collections.isEmpty;

public class MessengerPickAccountFragment extends AbstractAccountsFragment {

	@Nonnull
	public static final String FRAGMENT_TAG = "pick_account";

	private static final String ARG_ACCOUNT_IDS = "account_ids";

	public MessengerPickAccountFragment() {
		super("PickAccount", false, false);
	}

	@Nonnull
	public static MessengerPickAccountFragment newPickAccountFragment(@Nonnull Collection<Account> accounts) {
		final MessengerPickAccountFragment fragment = new MessengerPickAccountFragment();
		fragment.setArguments(createArguments(accounts));
		return fragment;
	}

	@Nonnull
	public static Bundle createArguments(@Nonnull Collection<Account> accounts) {
		final Bundle arguments = new Bundle();
		arguments.putStringArray(ARG_ACCOUNT_IDS, toArray(transform(accounts, new Function<Account, String>() {
			@Override
			public String apply(@Nullable Account account) {
				return account.getId();
			}
		}), String.class));
		return arguments;
	}


	@Nonnull
	@Override
	protected MessengerListItemAdapter<AccountListItem> createAdapter() {
		final List<AccountListItem> listItems = new ArrayList<AccountListItem>();

		final String[] accountIds = getArguments().getStringArray(ARG_ACCOUNT_IDS);
		if (!isEmpty(accountIds)) {
			for (String accountId : accountIds) {
				try {
					listItems.add(new AccountListItem(getAccountService().getAccountById(accountId), account_picked));
				} catch (UnsupportedAccountException e) {
					Log.e(getTag(), e.getMessage(), e);
				}
			}
		}

		return new AccountsAdapter(getActivity(), listItems, false, account_picked);
	}


	@Nullable
	@Override
	protected MessengerAsyncTask<Void, Void, List<Account>> createAsyncLoader(@Nonnull MessengerListItemAdapter<AccountListItem> adapter, @Nonnull Runnable onPostExecute) {
		return null;
	}
}
