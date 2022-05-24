package fr.epf.min1.deryne.myvelib;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class StationAdapter extends RecyclerView.Adapter<StationAdapter.ViewHolder> {

    private ArrayList<StationItem> stationItems;
    private Context context;
    private FavDB favDB;

    public StationAdapter(ArrayList<StationItem> stationItems, Context context){
        this.stationItems = stationItems;
        this.context = context;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        favDB = new FavDB(context);
        //d'abord crée une table
        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart",true);
        if (firstStart){
            createTableOnFirstStart();
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,
        parent, false);
        return new ViewHolder(view);
    }

    private void createTableOnFirstStart() {
        favDB.insertEmpty();

        SharedPreferences prefs = context.getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firststart", false);
        editor.apply();

    }

    @Override
    public void onBindViewHolder(@NonNull StationAdapter.ViewHolder holder, int position) {
    final StationItem stationItem = stationItems.get(position);

    readCursorData(stationItem, holder);
    holder.imageView.setImageResource(stationItem.getImageResource());
    holder.titleTextView.setText(stationItem.getTitle());


    }

    private void readCursorData(StationItem stationItem, ViewHolder viewHolder ) {
        Cursor cursor = favDB.read_all_data(stationItem.getKey_id());
        SQLiteDatabase db = favDB.getReadableDatabase();
        try {
            while (cursor.moveToNext()) {
                int columnIndex = cursor.getColumnIndex(FavDB.FAVORITE_STATUS);
                String item_fav_status = cursor.getString(columnIndex);
                stationItem.setFavStatus(item_fav_status);

                //aller voir le staus favori

                if (item_fav_status != null && item_fav_status.equals("1")) {
                    viewHolder.favBtn.setBackgroundResource(R.drawable.ic_favorite_red_24dp);
                } else if (item_fav_status != null && item_fav_status.equals("0")) {
                    viewHolder.favBtn.setBackgroundResource(R.drawable.ic_favorite_shadow_24dp);

                }
            }
        }finally {
            if (cursor!= null && cursor.isClosed())
                cursor.close();
            db.close();
        }

    }

    @Override
    public int getItemCount() {
        return stationItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView titleTextView;
        Button favBtn;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
           // imageView = itemView.findViewById(R.id.imageView);
            titleTextView = itemView.findViewById(R.id.titleTextView);
            favBtn = itemView.findViewById(R.id.favBtn);

            //ajouter à un button favoris

            favBtn.setOnClickListener(new View.OnClickListener() {


                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    StationItem stationItem = stationItems.get(position);

                    if (stationItem.getFavStatus().equals("0")) {
                        stationItem.setFavStatus("1");
                        favDB.insertIntoTheDatabase(stationItem.getTitle(), stationItem.getImageResource(),
                                stationItem.getKey_id(), stationItem.getFavStatus());
                        favBtn.setBackgroundResource(R.drawable.ic_favorite_red_24dp);
                    } else {
                        stationItem.setFavStatus("0");
                        favDB.remove_fav(stationItem.getKey_id());
                        favBtn.setBackgroundResource(R.drawable.ic_favorite_shadow_24dp);

                    }


                }
            });
        }


    }}
