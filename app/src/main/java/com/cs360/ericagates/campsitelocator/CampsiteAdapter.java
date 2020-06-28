package com.cs360.ericagates.campsitelocator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CampsiteAdapter extends RecyclerView.Adapter<CampsiteAdapter.CampsiteAdapterViewHolder> {
    List<Campsite> mCampsites = new ArrayList<Campsite>();

    public CampsiteAdapter(List<Campsite> campsites ){
        mCampsites = campsites;

    }
    /*
    cache of the view for Campsite list items
     */
    public class CampsiteAdapterViewHolder extends RecyclerView.ViewHolder {

        public final TextView campsiteItemNumberView; // Will display the position in the list, ie 1 through getItemCount()
        public final TextView campsiteNameView;
        public final TextView campsiteAddressView;
        public final TextView campsiteCityStateView;
        public final TextView campsiteZipView;
        public final TextView campsiteFeatureView;

       /* Constructor for our ViewHolder. Within this constructor, we get a reference to our
         * TextViews
         * @param itemView The View that was inflated in CampsiteAdapter#onCreateViewHolder
         */
        public CampsiteAdapterViewHolder(View itemView) {
            super(itemView);
            campsiteItemNumberView = (TextView) itemView.findViewById(R.id.tv_item_list_number);
            campsiteNameView = (TextView) itemView.findViewById(R.id.tv_siteName);
            campsiteAddressView = (TextView) itemView.findViewById(R.id.tv_Address);
            campsiteCityStateView = (TextView) itemView.findViewById(R.id.tv_cityState);
            campsiteZipView = (TextView) itemView.findViewById(R.id.tv_zip);
            campsiteFeatureView = (TextView) itemView.findViewById(R.id.tv_feature);
        }

        /**
         * This method will take an integer as input and
         * use that integer to display the appropriate text within a list item.
         * @param listIndex Position of the item in the list
         */
        void bind(int listIndex) {
            campsiteItemNumberView.setText(String.valueOf(listIndex+1));
        }
    }

    /**
     * This gets called when each new ViewHolder is created. This happens when the RecyclerView
     * is laid out. Enough ViewHolders will be created to fill the screen and allow for scrolling.
     *
     * @param viewGroup The ViewGroup that these ViewHolders are contained within.
     * @param viewType  If your RecyclerView has more than one type of item (which ours doesn't)
     * @return A new CampsiteAdapterViewHolder that holds the View for each list item
     */

    @Override
    public CampsiteAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Context context = viewGroup.getContext();
        int layoutIdForListen = R.layout.campsite_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        Boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListen, viewGroup, shouldAttachToParentImmediately);
        CampsiteAdapterViewHolder viewHolder = new CampsiteAdapterViewHolder(view);

        return viewHolder;
    }
    /**
     * OnBindViewHolder is called by the RecyclerView to display the data at the specified
     * position. In this method, we update the contents of the ViewHolder to display the weather
     * details for this particular position, using the "position" argument that is conveniently
     * passed into us.
     *
     * @param holder The ViewHolder which should be updated to represent the
     *                                  contents of the item at the given position in the data set.
     * @param position                  The position of the item within the adapter's data set.
     */

    @Override
    public void onBindViewHolder(CampsiteAdapterViewHolder holder, int position) {
        Campsite campsite = mCampsites.get(position);
        holder.bind(position);
        holder.campsiteNameView.setText(String.valueOf(campsite.getName()));
        holder.campsiteAddressView.setText(String.valueOf(campsite.getAddress()));
        holder.campsiteCityStateView.setText(String.valueOf(campsite.getCityState()));
        holder.campsiteZipView.setText(String.valueOf(campsite.getZip()));
        holder.campsiteFeatureView.setText(String.valueOf(campsite.getFeature()));

    }

    /**
     * This method simply returns the number of items to display. It is used behind the scenes
     * to help layout our Views and for animations.
     *
     * @return The number of items available in our forecast
     */
    @Override
    public int getItemCount() {
            return mCampsites.size();
    }



}
