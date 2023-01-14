package br.com.brunocarvalhs.payflow.features.billet_registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import br.com.brunocarvalhs.payflow.databinding.FragmentBilletRegistrationDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

// TODO: Customize parameter argument names
const val ARG_ITEM_COUNT = "item_count"

/**
 *
 * A fragment that shows a list of items as a modal bottom sheet.
 *
 * You can show this modal bottom sheet from your activity like this:
 * <pre>
 *    ItemListDialogFragment.newInstance(30).show(supportFragmentManager, "dialog")
 * </pre>
 */
class ItemListDialogFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBilletRegistrationDialogBinding? = null
    private val binding get() = requireNotNull(_binding)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBilletRegistrationDialogBinding.inflate(inflater, container, false)
        initView()
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initView() {
        binding.tryNew.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.enterCode.setOnClickListener {
            val action = ItemListDialogFragmentDirections
                .actionBilletRegistrationDialogFragmentToBilletRegistrationFormFragment(null)
            findNavController().navigate(action)
        }
    }
}