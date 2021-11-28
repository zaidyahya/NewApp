package com.example.theapp.ui.product.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.example.theapp.R
import com.example.theapp.databinding.ItemPagerImageBinding

class ProductImagesAdapter : PagerAdapter() {
    private val images: IntArray = intArrayOf(R.drawable.kurta_1, R.drawable.green_big, R.drawable.orange_small)

    override fun getCount(): Int {
        return images.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding = ItemPagerImageBinding.inflate(LayoutInflater.from(container.context), container, true)
        binding.apply {
            imageViewImage.setImageResource(images[position])
        }

        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as ViewGroup)
    }
}