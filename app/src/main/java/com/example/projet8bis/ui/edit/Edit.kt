package com.example.projet8bis.ui.edit

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.techradar.R
import dagger.hilt.android.AndroidEntryPoint



fun getDrawableUri(context: Context, drawableId: Int): Uri {
    return Uri.Builder().scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
        .authority(context.resources.getResourcePackageName(drawableId))
        .appendPath(context.resources.getResourceTypeName(drawableId))
        .appendPath(context.resources.getResourceEntryName(drawableId)).build()
}


@AndroidEntryPoint
class Edit : Fragment() {

    companion object {
        fun newInstance() = Edit()
    }

    private val viewModel: EditViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_edit, container, false)
    }
}