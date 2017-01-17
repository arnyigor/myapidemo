package com.arny.myapidemo.view.adapters;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import com.arny.myapidemo.R;

public class VolleyList extends ArrayAdapter<String> {
	private String[] ids;
	private String[] login;
	private String[] repos_url;
	private Activity context;

	// Класс для сохранения во внешний класс и для ограничения доступа
	// из потомков класса
	static class ViewHolder {
		public TextView tvId;
		public TextView tvLogin;
		public TextView tvRepos;
	}

	public VolleyList(Activity context, String[] ids, String[] login, String[] repos_url) {
		super(context, R.layout.volley_list_item, ids);
		this.context = context;
		this.ids = ids;
		this.login = login;
		this.repos_url = repos_url;

	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// ViewHolder буферизирует оценку различных полей шаблона элемента
		ViewHolder holder;
		// Очищает сущетсвующий шаблон, если параметр задан
		// Работает только если базовый шаблон для всех классов один и тот же
		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater inflater = context.getLayoutInflater();
			rowView = inflater.inflate(R.layout.volley_list_item, null, true);
			holder = new ViewHolder();
			holder.tvId = (TextView) rowView.findViewById(R.id.tvGitUserId);
			holder.tvLogin = (TextView) rowView.findViewById(R.id.tvGitUserId);
			holder.tvRepos = (TextView) rowView.findViewById(R.id.tvGitUserRepos);
			rowView.setTag(holder);
		} else {
			holder = (ViewHolder) rowView.getTag();
		}
		holder.tvId.setText(ids[position]);
		holder.tvLogin.setText(login[position]);
		holder.tvRepos.setText(repos_url[position]);
		return rowView;
	}
}
