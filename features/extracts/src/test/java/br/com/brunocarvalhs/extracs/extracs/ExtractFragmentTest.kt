package br.com.brunocarvalhs.extracs.extracs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import br.com.brunocarvalhs.domain.entities.CostEntities
import br.com.brunocarvalhs.extracts.databinding.FragmentExtractListBinding
import br.com.brunocarvalhs.extracts.extracts.ExtractFragment
import br.com.brunocarvalhs.extracts.extracts.ExtractRecyclerViewAdapter
import br.com.brunocarvalhs.extracts.extracts.ExtractViewModel
import br.com.brunocarvalhs.extracts.extracts.ExtractViewState
import com.google.android.material.search.SearchView
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyBoolean
import org.mockito.ArgumentMatchers.anyList
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.doNothing
import org.mockito.Mockito.mock
import org.mockito.Mockito.spy
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@MockitoInline.MocksAllowed
@RunWith(MockitoJUnitRunner::class)
class ExtractFragmentTest {

    private lateinit var fragment: ExtractFragment

    @Mock
    private lateinit var viewModel: ExtractViewModel

    @Mock
    private lateinit var adapter: ExtractRecyclerViewAdapter

    @Mock
    private lateinit var binding: FragmentExtractListBinding

    @Mock
    private lateinit var recyclerView: RecyclerView

    @Mock
    private lateinit var searchTextInputLayout: SearchView

    @Before
    fun setup() {
        fragment = spy(ExtractFragment())
        doNothing().`when`(fragment).setupList()
        doNothing().`when`(fragment).setupSearch()

        // Mock the layout binding and its views
        `when`(
            fragment.createBinding(
                Mockito.mock(LayoutInflater::class.java),
                Mockito.mock(ViewGroup::class.java),
                anyBoolean()
            )
        ).thenCallRealMethod()
        `when`(binding.list).thenReturn(recyclerView)
        `when`(binding.listSeachr).thenReturn(recyclerView)
        `when`(binding.searchView).thenReturn(searchTextInputLayout)

        // Mock the adapter and its methods
        `when`(fragment.adapter).thenReturn(adapter)
        `when`(adapter.filter(anyString())).thenCallRealMethod()
        `when`(adapter.submitList(anyList())).thenCallRealMethod()

        // Mock the viewModel LiveData
        val liveData = MutableLiveData<ExtractViewState>()
        `when`(viewModel.state).thenReturn(liveData)
    }

    @Test
    fun testViewObservation_LoadingState() {
        // Arrange
        val observer = mock<(ExtractViewState) -> Unit>()
        fragment.viewLifecycleOwnerLiveData.observeForever { lifecycleOwner ->
            if (lifecycleOwner != null) {
                fragment.viewObservation()
                fragment.viewModel.state.observe(lifecycleOwner, observer)
            }
        }

        // Act
        fragment.onViewCreated(mock(), mock())

        // Assert
        verify(observer).invoke(ExtractViewState.Loading)
        verify(fragment).loading()
    }

    @Test
    fun testViewObservation_SuccessState() {
        // Arrange
        val observer = mock<(ExtractViewState) -> Unit>()
        fragment.viewLifecycleOwnerLiveData.observeForever { lifecycleOwner ->
            if (lifecycleOwner != null) {
                fragment.viewObservation()
                fragment.viewModel.state.observe(lifecycleOwner, observer)
            }
        }
        val list = listOf<CostEntities>(mock())

        // Act
        fragment.onViewCreated(mock(), mock())
        fragment.displayData(list)

        // Assert
        verify(observer).invoke(ExtractViewState.Success(list))
        verify(fragment.adapter).submitList(list)
    }

    @Test
    fun testViewObservation_ErrorState() {
        val observer = mock<(ExtractViewState) -> Unit>()
        fragment.viewLifecycleOwnerLiveData.observeForever { lifecycleOwner ->
            if (lifecycleOwner != null) {
                fragment.viewObservation()
                fragment.viewModel.state.observe(lifecycleOwner, observer)
            }
        }
    }
}