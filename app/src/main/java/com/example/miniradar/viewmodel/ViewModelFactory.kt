package com.example.miniradar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.miniradar.data.repository.PersonRepository

class ViewModelFactory constructor(private val repository: PersonRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AgentsCardViewModel::class.java)) {
            AgentsCardViewModel(repository = this.repository) as T
        } else {
            throw IllegalArgumentException("ViewModel not found!")
        }
    }
}


//internal class ZPlatformViewModelFactory : ViewModelProvider.Factory {
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        @Suppress("UNCHECKED_CAST")
//        return when {
//            modelClass.isAssignableFrom(ZPlatformBaseActivityViewModel::class.java) -> {
//                if(activityViewModel == null) {
//                    activityViewModel = ZPlatformBaseActivityViewModel()
//                }
//                activityViewModel as T
//            }
//            else -> {
//                if(viewModel == null) {
//                    viewModel = when {
//                        modelClass.isAssignableFrom(ZPlatformDetailViewModel::class.java) -> ZPlatformDetailViewModel()
//                        modelClass.isAssignableFrom(ZPlatformListViewModel::class.java) -> ZPlatformListViewModel()
//                        modelClass.isAssignableFrom(ZPlatformEditListViewModel::class.java) -> ZPlatformEditListViewModel()
//                        modelClass.isAssignableFrom(ZPlatformBottomSheetViewModel::class.java) -> ZPlatformBottomSheetViewModel()
//                        else -> throw IllegalArgumentException("Unknown ViewModel class")
//                    }
//                }
//
//                viewModel as T
//            }
//        }
//    }
//}