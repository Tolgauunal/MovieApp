package com.unallapps.movieapp.ui.Home.Adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager.widget.PagerAdapter
import coil.ImageLoader
import coil.load
import com.example.viewmodel3.Movie
import com.unallapps.movieapp.R
import java.util.Objects

class ImageSliderTopAdapter(
    val context: Context,
    val imageList: List<Movie>,
    val onClick: (movie: Movie) -> Unit,
    val play:(movie:Movie)->Unit
) : PagerAdapter() {

    //ImageSize
    override fun getCount(): Int {
        return 5
    }

    //verilem nesneyle objeyi eşleştiriyoruz tasarımla activity viewi
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object` as ConstraintLayout
    }


    //Bu metot, bir görüntü nesnesini oluşturur ve ViewPager içine yerleştirir. Görüntü, imageList içinden alınır ve imageView nesnesine atanır.
    @SuppressLint("ServiceCast", "MissingInflatedId", "SetTextI18n", "RestrictedApi")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val imageList = imageList[position]
        val mLayoutInflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemView: View = mLayoutInflater.inflate(R.layout.image_slider_top, container, false)
        val imageViewBanner: ImageView = itemView.findViewById<View>(R.id.idIVBanner) as ImageView
        val movieName: TextView =
            itemView.findViewById(R.id.movieNameImageSlider1) as TextView
        val movieCategoryName: TextView =
            itemView.findViewById(R.id.movieCategoryNameImageSlider1) as TextView

        val moviePlayIntent: ImageView = itemView.findViewById(R.id.imgPlay) as ImageView

        movieName.text = context.getString(imageList.name)
        movieCategoryName.text = imageList.category1.toString()
        imageViewBanner.load(imageList.banner)

        Objects.requireNonNull(container).addView(itemView)

        itemView.setOnClickListener {
            onClick(imageList)
        }
        moviePlayIntent.setOnClickListener{
            play(imageList)
        }

        return itemView
    }


    //Bu metot, ViewPager'dan bir görüntü nesnesini kaldırmak için kullanılır. Metot, container içinden (object) belirtilen görüntüyü kaldırır.
    //KAydırma sırasında bir önceki metodu yoketme
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ConstraintLayout)
    }
}