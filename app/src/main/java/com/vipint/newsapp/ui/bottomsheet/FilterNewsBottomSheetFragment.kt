package com.vipint.newsapp.ui.bottomsheet

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.vipint.newsapp.databinding.NewsTypeBottomSheetBinding
import com.vipint.newsapp.utils.NewsType

class FilterNewsBottomSheetFragment : BottomSheetDialogFragment() {
    private val typeList: List<NewsType> by lazy {
        (arguments?.getStringArray(ARG_TYPE_LIST) ?: emptyArray()).mapNotNull {
            try {
                NewsType.fromString(it)
            } catch (e: IllegalArgumentException) {
                null
            }
        }
    }
    var onNewsSourceClick: (NewsType) -> Unit = {}
    private lateinit var _binding: NewsTypeBottomSheetBinding
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        requireActivity().window?.setDimAmount(0.4f)
        return super.onCreateDialog(savedInstanceState)
    }

    companion object {
        private const val ARG_TYPE_LIST = "type_list"

        fun newInstance(typeList: List<NewsType>): FilterNewsBottomSheetFragment {
            return FilterNewsBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putStringArray(ARG_TYPE_LIST, typeList.map { it.type }.toTypedArray())
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = NewsTypeBottomSheetBinding.inflate(layoutInflater)
        initView()
        return _binding.root
    }

    private fun initView() {
        _binding.apply {
            val newsTypeAdapter = NewsTypeAdapter(typeList) {
                onNewsSourceClick.invoke(it)
                dismiss()
            }
            rvNewsType.apply {
                adapter = newsTypeAdapter
                hasFixedSize()
            }
        }
    }


}