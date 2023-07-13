package tech.driskimaulana.suitmediamobiletest.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView
import tech.driskimaulana.suitmediamobiletest.R
import tech.driskimaulana.suitmediamobiletest.interefaces.OnBackPressedListener
import tech.driskimaulana.suitmediamobiletest.data.models.RegresinModel


class RecViewAdapter(items: ArrayList<RegresinModel?>, private  val onBackPressedListener: OnBackPressedListener) : RecyclerView.Adapter<RecViewAdapter.ItemViewHolder>() {

    var mItemList: ArrayList<RegresinModel?>? = null

    init {
        if (mItemList == null) {
            mItemList = items;
        }else {
            if (items != null) {
                for (i in items) {
                    mItemList!!.add(i);
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {

        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.recview_item, parent, false)
        return  ItemViewHolder(view)

    }

    override fun onBindViewHolder(viewHolder: ItemViewHolder, position: Int) {
        populateItemRows(viewHolder, position);
    }

    override fun getItemCount(): Int {
        return if (mItemList == null) 0 else mItemList!!.size
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var tvFullname: TextView;
        lateinit var tvEmail: TextView;
        lateinit var imgProfile: CircleImageView;

        init {
            tvFullname = itemView.findViewById<TextView>(R.id.tv_fullname);
            tvEmail = itemView.findViewById<TextView>(R.id.tv_email);
            imgProfile = itemView.findViewById<CircleImageView>(R.id.iv_profile);
        }
    }


    private fun populateItemRows(viewHolder: ItemViewHolder, position: Int) {
        val item = mItemList!![position]
        if (item != null) {
            viewHolder.tvEmail.text = item.email;
            viewHolder.tvFullname.text = "${item.firstName} ${item.lastName}";

            Glide.with(viewHolder.itemView)
                .load(item.avatar)
                .placeholder(R.drawable.profile)
                .error(R.drawable.profile)
                .centerCrop()
                .into(viewHolder.imgProfile)

            viewHolder.itemView.setOnClickListener {
                onBackPressedListener.onBackPressedWithData("${item.firstName} ${item.lastName}")
            }
        };
    }

}