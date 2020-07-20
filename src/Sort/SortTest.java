package Sort;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import java.util.ArrayList;
import org.junit.Test;
import Sort.Exceptions.InvalidInputException;
import Sort.Exceptions.NotPowerOfTwoException;
import Sort.Exceptions.NoExceptionCaught;

@SuppressWarnings({ "deprecation" })
public class SortTest {
	private SortingAlgorithms _algorithm;

	public SortTest() {
	}

	public void listOfClassesToTestSize(int j, ArrayList<Integer> listToSort, boolean bool, int resultSize, int k,
			ArrayList<Integer> sortedList) throws InvalidInputException, NotPowerOfTwoException {
		switch (k) {
		case 0:
			_algorithm = new AmericanFlagSort(j, listToSort, bool);
			assertThat(_algorithm.returnSortedList().size(), is(resultSize));
			assertThat(_algorithm.returnSortedList(), is(sortedList));
			break;
		case 1:
			_algorithm = new BitonicSort(j, listToSort);
			assertThat(_algorithm.returnSortedList().size(), is(resultSize));
			assertThat(_algorithm.returnSortedList(), is(sortedList));
			break;
		case 2:
			_algorithm = new BogoSort(j, listToSort);
			assertThat(_algorithm.returnSortedList().size(), is(resultSize));
			assertThat(_algorithm.returnSortedList(), is(sortedList));
			break;
		case 3:
			_algorithm = new BozoSort(j, listToSort);
			assertThat(_algorithm.returnSortedList().size(), is(resultSize));
			assertThat(_algorithm.returnSortedList(), is(sortedList));
			break;
		case 4:
			_algorithm = new BubbleSort(j, listToSort);
			assertThat(_algorithm.returnSortedList().size(), is(resultSize));
			assertThat(_algorithm.returnSortedList(), is(sortedList));
			break;
		case 5:
			_algorithm = new BubbleSort2(j, listToSort);
			assertThat(_algorithm.returnSortedList().size(), is(resultSize));
			assertThat(_algorithm.returnSortedList(), is(sortedList));
			break;
		case 6:
			_algorithm = new BucketSort(j, listToSort, bool);
			assertThat(_algorithm.returnSortedList().size(), is(resultSize));
			assertThat(_algorithm.returnSortedList(), is(sortedList));
			break;
		case 7:
			_algorithm = new CocktailSort(j, listToSort);
			assertThat(_algorithm.returnSortedList().size(), is(resultSize));
			assertThat(_algorithm.returnSortedList(), is(sortedList));
			break;
		case 8:
			_algorithm = new CombSort(j, listToSort);
			assertThat(_algorithm.returnSortedList().size(), is(resultSize));
			assertThat(_algorithm.returnSortedList(), is(sortedList));
			break;
		case 9:
			_algorithm = new CountSort(j, listToSort);
			assertThat(_algorithm.returnSortedList().size(), is(resultSize));
			assertThat(_algorithm.returnSortedList(), is(sortedList));
			break;
		case 10:
			_algorithm = new CycleSort(j, listToSort);
			assertThat(_algorithm.returnSortedList().size(), is(resultSize));
			assertThat(_algorithm.returnSortedList(), is(sortedList));
			break;
		case 11:
			_algorithm = new HeapSort(j, listToSort);
			assertThat(_algorithm.returnSortedList().size(), is(resultSize));
			assertThat(_algorithm.returnSortedList(), is(sortedList));
			break;
		case 12:
			_algorithm = new HoareQuickSort(j, listToSort);
			assertThat(_algorithm.returnSortedList().size(), is(resultSize));
			assertThat(_algorithm.returnSortedList(), is(sortedList));
			break;
		case 13:
			_algorithm = new HybridQuickSort(j, listToSort);
			assertThat(_algorithm.returnSortedList().size(), is(resultSize));
			assertThat(_algorithm.returnSortedList(), is(sortedList));
			break;
		case 14:
			_algorithm = new InsertionSort(j, listToSort);
			assertThat(_algorithm.returnSortedList().size(), is(resultSize));
			assertThat(_algorithm.returnSortedList(), is(sortedList));
			break;
		case 15:
			_algorithm = new IntroSort(j, listToSort);
			assertThat(_algorithm.returnSortedList().size(), is(resultSize));
			assertThat(_algorithm.returnSortedList(), is(sortedList));
			break;
		case 16:
			_algorithm = new LomutoQuickSort(j, listToSort);
			assertThat(_algorithm.returnSortedList().size(), is(resultSize));
			assertThat(_algorithm.returnSortedList(), is(sortedList));
			break;
		case 17:
			_algorithm = new LSDRadixSort(j, listToSort, bool);
			assertThat(_algorithm.returnSortedList().size(), is(resultSize));
			assertThat(_algorithm.returnSortedList(), is(sortedList));
			break;
		case 18:
			_algorithm = new MergeSort(j, listToSort, bool);
			assertThat(_algorithm.returnSortedList().size(), is(resultSize));
			assertThat(_algorithm.returnSortedList(), is(sortedList));
			break;
		case 19:
			_algorithm = new OddEvenSort(j, listToSort);
			assertThat(_algorithm.returnSortedList().size(), is(resultSize));
			assertThat(_algorithm.returnSortedList(), is(sortedList));
			break;
		case 20:
			_algorithm = new OddEvenMergeSort(j, listToSort);
			assertThat(_algorithm.returnSortedList().size(), is(resultSize));
			assertThat(_algorithm.returnSortedList(), is(sortedList));
			break;
		case 21:
			_algorithm = new PancakeSort(j, listToSort);
			assertThat(_algorithm.returnSortedList().size(), is(resultSize));
			assertThat(_algorithm.returnSortedList(), is(sortedList));
			break;
		case 22:
			_algorithm = new PigeonholeSort(j, listToSort, bool);
			assertThat(_algorithm.returnSortedList().size(), is(resultSize));
			assertThat(_algorithm.returnSortedList(), is(sortedList));
			break;
		case 23:
			_algorithm = new SelectionSort(j, listToSort);
			assertThat(_algorithm.returnSortedList().size(), is(resultSize));
			assertThat(_algorithm.returnSortedList(), is(sortedList));
			break;
		case 24:
			_algorithm = new ShellSort(j, listToSort);
			assertThat(_algorithm.returnSortedList().size(), is(resultSize));
			assertThat(_algorithm.returnSortedList(), is(sortedList));
			break;
		case 25:
			// assertThat(new SleepSort(j,
			// listToSort).returnSortedList().size(), is(resultSize));
			break;
		case 26:
			_algorithm = new SlowSort(j, listToSort);
			assertThat(_algorithm.returnSortedList().size(), is(resultSize));
			assertThat(_algorithm.returnSortedList(), is(sortedList));
			break;
		case 27:
			_algorithm = new StoogeSort(j, listToSort);
			assertThat(_algorithm.returnSortedList().size(), is(resultSize));
			break;
		case 28:
			_algorithm = new StrandSort(j, listToSort, bool);
			assertThat(_algorithm.returnSortedList().size(), is(resultSize));
			assertThat(_algorithm.returnSortedList(), is(sortedList));
			break;
		case 29:
			_algorithm = new TimSort(j, listToSort, bool);
			assertThat(_algorithm.returnSortedList().size(), is(resultSize));
			assertThat(_algorithm.returnSortedList(), is(sortedList));
			break;
		case 30:
			_algorithm = new ThreewayMergeSort(j, listToSort, bool);
			assertThat(_algorithm.returnSortedList().size(), is(resultSize));
			assertThat(_algorithm.returnSortedList(), is(sortedList));
			break;
		case 31:
			_algorithm = new ThreeWayQuickSort(j, listToSort);
			assertThat(_algorithm.returnSortedList().size(), is(resultSize));
			assertThat(_algorithm.returnSortedList(), is(sortedList));
			break;
		}
	}

	@Test
	public void noNumberToSortTest() throws InvalidInputException, NotPowerOfTwoException {
		for (int i = 0; i < 32; i++) {
			if (i != 1 && i != 20) {
				ArrayList<Integer> listToSort = new ArrayList<>();
				this.listOfClassesToTestSize(listToSort.size(), listToSort, false, 0, i, listToSort);
			}
		}
	}

	@Test
	public void preSortedNumToSortTest() throws InvalidInputException, NotPowerOfTwoException {
		for (int i = 0; i < 32; i++) {
			ArrayList<Integer> listToSort = new ArrayList<>();
			listToSort.add(5);
			listToSort.add(7);
			listToSort.add(9);
			listToSort.add(10);
			ArrayList<Integer> sln = new ArrayList<>();
			sln.add(5);
			sln.add(7);
			sln.add(9);
			sln.add(10);
			this.listOfClassesToTestSize(listToSort.size(), listToSort, false, 4, i, sln);
		}
	}

	@Test
	public void sortNullListTest() throws NotPowerOfTwoException, NoExceptionCaught {
		boolean tempCaughtException = false;
		for (int i = 0; i < 32; i++) {
			try {
				this.listOfClassesToTestSize(0, null, false, 0, i, null);
			} catch (InvalidInputException e) {
				tempCaughtException = true;
			} finally {
				if (tempCaughtException == false) {
					throw new NoExceptionCaught("");
				}
			}
		}
	}

}
