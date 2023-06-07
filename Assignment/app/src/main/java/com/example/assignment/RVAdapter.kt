package com.example.assignment

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import com.example.assignment.R
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.example.components2.Restaurant
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class RVAdapter(private val list: ArrayList<Restaurant>,
                val context: Context,
                val savedInstanceState: Bundle?) :
    RecyclerView.Adapter<RVAdapter.MyViewHolder>() {

//    lateinit var marker: MarkerOptions

    class MyViewHolder(view: View, val context: Context) : RecyclerView.ViewHolder(view) {
        var name = view.findViewById<TextView>(R.id.item_tv_exp)
        var type = view.findViewById<TextView>(R.id.item_tv_type)
        var moreinfo = view.findViewById<TextView>(R.id.item_tv_more_info)
        var lessinfo = view.findViewById<TextView>(R.id.item_tv_less_info)
        var authorityName = view.findViewById<TextView>(R.id.item_tv_authority_name)
        var hygiene = view.findViewById<TextView>(R.id.item_tv_score_hygine)
        var structural = view.findViewById<TextView>(R.id.item_tv_score_structural)
        var management = view.findViewById<TextView>(R.id.item_tv_score_management)
        var website = view.findViewById<TextView>(R.id.item_tv_website)
        var rating = view.findViewById<RatingBar>(R.id.item_rating)
        var ll_exp_sub = view.findViewById<LinearLayout>(R.id.ll_description)
        var email_to = view.findViewById<TextView>(R.id.et_email_to)
        var email_subject = view.findViewById<EditText>(R.id.et_email_subject)
        var email_message = view.findViewById<EditText>(R.id.et_email_message)
        var btn_send = view.findViewById<ImageButton>(R.id.btn_email_send)
        var map = view.findViewById<ImageView>(R.id.item_map)
//        var map = (context as FragmentActivity).supportFragmentManager.findFragmentById(R.id.item_map) as SupportMapFragment
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_restaurents, parent, false)

        return MyViewHolder(view, context)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val restaurant = list[position]

        holder.name.text = restaurant.name
        holder.type.text = restaurant.type
        holder.authorityName.text = restaurant.authority
        holder.hygiene.text = restaurant.score[0]
        holder.structural.text = restaurant.score[1]
        holder.management.text = restaurant.score[2]
        holder.website.text = restaurant.website
        holder.rating.rating =
            if (restaurant.rating == "Exempt" || restaurant.rating == "AwaitingInspection") 0f else restaurant.rating.toFloat()
        holder.email_to.text = "To: " + restaurant.email
        holder.map.setImageResource(R.drawable.ic_baseline_image_24)


        val isExpandable: Boolean = restaurant.expandable
        holder.ll_exp_sub.visibility = if (isExpandable) View.VISIBLE else View.GONE
        holder.moreinfo.visibility = if (isExpandable) View.GONE else View.VISIBLE

        if(isExpandable) {
            var url = "http://maps.google.com/maps/api/staticmap?center=" +
                    restaurant.latitude +
                    "," +
                    restaurant.longitude +
                    "&zoom=15&size=200x200&sensor=false" +
                    "&markers=size:mid%7Ccolor:red%7Clabel:C%7C" +
                    restaurant.latitude +
                    "," +
                    restaurant.longitude +
                    "&key=AIzaSyCMd-C6BU6Enr45lgIaRg_1ozQt9ofOsmk"

            Log.e("url", url)

            Glide
                .with(holder.itemView.context)
                .load(url)
                .centerCrop()
                .into(holder.map)
        }

        holder.map.setOnClickListener {
            val intent = Intent(context, MapActivity::class.java)
            intent.putExtra("latitude", restaurant.latitude)
            intent.putExtra("longitude", restaurant.longitude)

            context.startActivity(intent)
        }

        holder.moreinfo.setOnClickListener {
            restaurant.expandable = true
            Log.e("loc", restaurant.latitude + " " + restaurant.longitude)

            notifyItemChanged(position)

//            marker = MarkerOptions()
//            marker.position(LatLng(restaurant.latitude.toDouble(), restaurant.longitude.toDouble()))

//            holder.map.getMapAsync(this)
//            holder.map.onCreate(savedInstanceState)
        }

        holder.lessinfo.setOnClickListener {
            restaurant.expandable = false
            notifyItemChanged(position)
        }

        holder.btn_send.setOnClickListener {
//            val recipients: Array<String> = recipientList.split(",") as Array<String>
            val recipients = arrayOf("malhar.dharmadhikari@gmail.com")
            val subject = holder.email_subject.text.toString()
            val message = holder.email_message.text.toString()

            var intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_EMAIL, recipients)
            intent.putExtra(Intent.EXTRA_SUBJECT, subject)
            intent.putExtra(Intent.EXTRA_TEXT, message)

            intent.setType("message/rfc822")
            context.startActivity(Intent.createChooser(intent, "Choose Email Client"))
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}