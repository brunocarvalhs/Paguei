package br.com.brunocarvalhs.payflow.features.costs.selected_cost

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import br.com.brunocarvalhs.commons.BaseBottomSheetDialogFragment
import br.com.brunocarvalhs.payflow.databinding.DialogCostsSelectedBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CostsSelectedDialogFragment :
    BaseBottomSheetDialogFragment<DialogCostsSelectedBinding>() {

    override fun createBinding(
        inflater: LayoutInflater, container: ViewGroup?, attachToParent: Boolean
    ): DialogCostsSelectedBinding =
        DialogCostsSelectedBinding.inflate(inflater, container, attachToParent)

    override fun initView() {

    }

    override fun viewObservation() {

    }

    override fun argumentsView(arguments: Bundle) {

    }

    override fun loading() {

    }
}