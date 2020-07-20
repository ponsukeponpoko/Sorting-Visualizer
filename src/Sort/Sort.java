package Sort;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;
import Sort.Exceptions.InvalidInputException;
import Sort.Exceptions.NotPowerOfTwoException;
import javafx.animation.Animation;
import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class Sort {

	private Pane _root;
	private ArrayList<Rectangles> _rectangles = new ArrayList<Rectangles>();
	private ArrayList<Integer> _numToSort;
	private int _numOfBars;
	private int _maxVal;
	private double _widthOfBars;
	private KeyFrame _movement;
	private Timeline _timeline;
	private Iterator<Swap> _iterator;
	private Iterator<VisualChange> _iteratorForNonComparison;
	private int _timelineCounter;
	private int _toPinkIndex;
	private boolean _alreadyVisited;
	private Swap _swap;
	private VisualChange _change;
	private boolean _isPaused;
	private int _pauseCount;
	private static AlgorithmMode _mode;
	private boolean _timelineInitialized;
	private boolean _isComparisonBased;
	private int _combIndex;
	private int _storeAmericanFlagIndex;
	private double _timelineSpeed;

	@SuppressWarnings("static-access")
	public Sort(Pane root) {
		_root = root;
		_numToSort = new ArrayList<Integer>();
		_numOfBars = 50;
		_maxVal = 0;
		_widthOfBars = Constants.APP_WIDTH / _numOfBars;
		_timelineCounter = 0;
		_toPinkIndex = 0;
		_pauseCount = 0;
		_isPaused = false;
		_isComparisonBased = true;
		_timelineInitialized = false;
		_root.setOnKeyPressed(new KeyHandler());
		_root.setFocusTraversable(true);
		_timelineSpeed = 1000;
		_mode = _mode.NULL;
		this.makeGraph(_numOfBars, false, null);
	}

	public void clearGraph() {
		if (_timelineInitialized) {
			_timeline.stop();
		}
		for (int i = 0; i < _rectangles.size(); i++) {
			Rectangles currRect = _rectangles.get(i);
			_rectangles.remove(currRect);
			_root.getChildren().removeAll(currRect.getRects());
		}
		_root.getChildren().clear();
		_rectangles.clear();
		_numToSort = new ArrayList<Integer>();
		_numOfBars = 0;
		_maxVal = 0;
		_widthOfBars = 0;
		_timelineInitialized = false;
		_timelineCounter = 0;
		_toPinkIndex = 0;
		_pauseCount = 0;
		_storeAmericanFlagIndex = 0;
		_isComparisonBased = true;
		_isPaused = false;
		_root.requestFocus();
		_root.setFocusTraversable(true);
	}

	public void makeGraph(int numOfBars, boolean hasInput, ArrayList<Integer> numToSort) {
		_numOfBars = numOfBars;
		_widthOfBars = (double) Constants.APP_WIDTH / _numOfBars;
		//_widthOfBarw = (double)
		for (int i = 0; i < _numOfBars; i++) {
			int currInteger = (int) (Math.random() * 2000);
			if (hasInput) {
				currInteger = numToSort.get(i);
			}
			_numToSort.add(currInteger);
			if (currInteger > _maxVal) {
				_maxVal = currInteger;
			}
		}
		double currentXVal = 0;
		for (int i = 0; i < _numOfBars; i++) {
			Rectangles newRectangle = new Rectangles(_root,
					(double) (Constants.APP_HEIGHT - Constants.BUTTON_HEIGHT) * _numToSort.get(i) / _maxVal,
					(double) _widthOfBars);
			newRectangle.setXLoc((double) currentXVal);
			newRectangle.setYLoc((double) Constants.APP_HEIGHT
					- (Constants.APP_HEIGHT - Constants.BUTTON_HEIGHT) * _numToSort.get(i) / _maxVal - 30);
			currentXVal += _widthOfBars;
			_rectangles.add(newRectangle);
		}
	}

	private void setUpTimeline() {
		/*
		 * This method sets up timeline for the game.
		 */
		_movement = new KeyFrame(Duration.millis(_timelineSpeed), new TimeHandler());
		_timeline = new Timeline(_movement);
		_timeline.setCycleCount(Animation.INDEFINITE);
		_timeline.play();
	}

	private void setUpTimelineForNonComparison() {
		_movement = new KeyFrame(Duration.millis(_timelineSpeed), new TimeHandler2());
		_timeline = new Timeline(_movement);
		_timeline.setCycleCount(Animation.INDEFINITE);
		_timeline.play();
	}

	private class TimeHandler implements EventHandler<ActionEvent> {
		// @Override
		@SuppressWarnings("static-access")
		public void handle(ActionEvent event) {
			if (_iterator.hasNext()) {
				_alreadyVisited = false;
				if (_timelineCounter == 0) {
					_swap = _iterator.next();
					_rectangles.get(_swap._lowerIndex).setColor(Color.BLUE);
					if (_mode == _mode.COMB || _mode == _mode.PANCAKE || _mode == _mode.LOMUTO_QUICK
							|| _mode == _mode.HOARE_QUICK || _mode == _mode.HEAP || _mode == _mode.COCKTAIL
							|| _mode == _mode.SHELL || _mode == _mode.SLOW || _mode == _mode.THREEWAY_QUICK
							|| _mode == _mode.INTRO || _mode == _mode.BOGO || _mode == _mode.STOOGE
							|| _mode == _mode.BOZO || _mode == _mode.SELECTION || _mode == _mode.ODD_EVEN
							|| _mode == _mode.BITONIC || _mode == _mode.BATCHER_ODD_EVEN_MERGE) {
						_combIndex = _swap._lowerIndex + _swap._val2;
						_rectangles.get(_swap._lowerIndex + _swap._val2).setColor(Color.BLUE);
					} else if (_swap._lowerIndex + 1 < _numOfBars) {
						_rectangles.get(_swap._lowerIndex + 1).setColor(Color.BLUE);
					}
					_toPinkIndex = _swap._lowerIndex;
					_timelineCounter++;
					_alreadyVisited = true;
				}
				if (_timelineCounter == 1 && !_alreadyVisited) {
					if (_swap._bool == true) {
						Rectangles tempRect = _rectangles.get(_swap._lowerIndex);
						_rectangles.set(_swap._lowerIndex, _rectangles.get(_swap._lowerIndex + _swap._val2));
						_rectangles.set(_swap._lowerIndex + _swap._val2, tempRect);
						_rectangles.get(_swap._lowerIndex)
								.setXLoc(_rectangles.get(_swap._lowerIndex).getXLoc() - (_widthOfBars * (_swap._val2)));
						_rectangles.get(_swap._lowerIndex + _swap._val2)
								.setXLoc(_rectangles.get(_swap._lowerIndex + _swap._val2).getXLoc()
										+ (_widthOfBars * (_swap._val2)));

					}
					_timelineCounter++;
					_alreadyVisited = true;
					_rectangles.get(_numOfBars - 1).setColor(Color.PINK);
				}
				if (_timelineCounter == 2 && !_alreadyVisited) {
					_rectangles.get(_toPinkIndex).setColor(Color.PINK);
					_rectangles.get(_numOfBars - 1).setColor(Color.PINK);
					_timelineCounter = 0;
					if (_mode == _mode.INSERTION) {
						_rectangles.get(_toPinkIndex + 1).setColor(Color.PINK);
					}
					if (_mode == _mode.COMB || _mode == _mode.PANCAKE || _mode == _mode.LOMUTO_QUICK
							|| _mode == _mode.HOARE_QUICK || _mode == _mode.HEAP || _mode == _mode.COCKTAIL
							|| _mode == _mode.SHELL || _mode == _mode.SLOW || _mode == _mode.THREEWAY_QUICK
							|| _mode == _mode.INTRO || _mode == _mode.BOGO || _mode == _mode.STOOGE
							|| _mode == _mode.BOZO || _mode == _mode.SELECTION || _mode == _mode.ODD_EVEN
							|| _mode == _mode.BITONIC || _mode == _mode.BATCHER_ODD_EVEN_MERGE) {
						_rectangles.get(_combIndex).setColor(Color.PINK);
					}
				}
			} else {
				System.out.println("DONE");
				_timeline.stop();
			}
			_alreadyVisited = false;
		}
	}

	private class TimeHandler2 implements EventHandler<ActionEvent> {
		@Override
		@SuppressWarnings("static-access")
		public void handle(ActionEvent event) {
			if (_iteratorForNonComparison.hasNext()) {
				_alreadyVisited = false;
				if (_timelineCounter == 0) {
					_change = _iteratorForNonComparison.next();
					if (_mode == _mode.AMERICAN_FLAG || _mode == _mode.TIM || _mode == _mode.HYBRID_QUICK
							|| _mode == _mode.CYCLE || _mode == _mode.STRAND) {
						_rectangles.get(_storeAmericanFlagIndex).setColor(Color.PINK);
						_storeAmericanFlagIndex = _change._currIndex;
					}
					if (_change._currIndex != 0) {
						_rectangles.get(_change._currIndex - 1).setColor(Color.PINK);
					} else {
						_rectangles.get(_numOfBars - 1).setColor(Color.PINK);
					}
					_rectangles.get(_change._currIndex).setColor(Color.BLUE);
					_timelineCounter++;
					if (_change._notScanning == false) {
						_timelineCounter = 0;
					}
					_alreadyVisited = true;
				}
				if (_timelineCounter == 1 && !_alreadyVisited) {
					Rectangles tempRect = _rectangles.get(_change._currIndex);
					tempRect.setHeight(
							(double) (Constants.APP_HEIGHT - Constants.BUTTON_HEIGHT) * _change._newVal / _maxVal);
					tempRect.setYLoc((double) Constants.APP_HEIGHT
							- (Constants.APP_HEIGHT - Constants.BUTTON_HEIGHT) * _change._newVal / _maxVal - 30);
					_timelineCounter = 0;
				}
			} else {
				System.out.println("DONE");
				_timeline.stop();
			}
			_alreadyVisited = false;
		}
	}

	@SuppressWarnings("static-access")
	private void setUpAlert(AlgorithmMode mode) throws InvalidInputException {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirmation Dialog");
		alert.setHeaderText("Too Many Numbers to Sort");
		alert.setContentText(
				"Sorting over 10 numbers using this algorithm is not recommended. Click cancel to re-setup visualizer. Click OK to continue sorting.");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.CANCEL) {
			this.clearGraph();
			_mode = _mode.NULL;
		} else {
			if (mode == AlgorithmMode.BOGO) {
				_iterator = new BogoSort(_numOfBars, _numToSort).sortAlgorithm().iterator();
			}
			if (mode == AlgorithmMode.BOZO) {
				_iterator = new BozoSort(_numOfBars, _numToSort).sortAlgorithm().iterator();
			}
		}
	}

	private void setUpAlertForBatcher() {
		Alert alert = new Alert(AlertType.WARNING);
		alert.setTitle("Warning!");
		alert.setHeaderText("Invalid array size");
		alert.setContentText(
				"In order to sort numbers using Odd-even merge sort or bitonic sort, the size of array must be in power of 2");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.OK) {
			this.clearGraph();
			_mode = AlgorithmMode.NULL;
		}

	}
	
	public void setUpTimelineSpeed(double val) {
		_timelineSpeed = val;
		if (_timelineInitialized) {
			_timeline.setRate(1000/val);
		}
	}

	private void checkAlgorithm() throws InvalidInputException {
		switch (_mode) {
		case PANCAKE:
			_iterator = new PancakeSort(_numOfBars, _numToSort).sortAlgorithm().iterator();
			break;
		case BUBBLE:
			_iterator = new BubbleSort(_numOfBars, _numToSort).sortAlgorithm().iterator();
			break;
		case BUBBLE2:
			_iterator = new BubbleSort2(_numOfBars, _numToSort).sortAlgorithm().iterator();
			break;
		case LOMUTO_QUICK:
			_iterator = new LomutoQuickSort(_numOfBars, _numToSort).sortAlgorithm().iterator();
			break;
		case HOARE_QUICK:
			_iterator = new HoareQuickSort(_numOfBars, _numToSort).sortAlgorithm().iterator();
			break;
		case INSERTION:
			_iterator = new InsertionSort(_numOfBars, _numToSort).sortAlgorithm().iterator();
			break;
		case COMB:
			_iterator = new CombSort(_numOfBars, _numToSort).sortAlgorithm().iterator();
			break;
		case HEAP:
			_iterator = new HeapSort(_numOfBars, _numToSort).sortAlgorithm().iterator();
			break;
		case COCKTAIL:
			_iterator = new CocktailSort(_numOfBars, _numToSort).sortAlgorithm().iterator();
			break;
		case SHELL:
			_iterator = new ShellSort(_numOfBars, _numToSort).sortAlgorithm().iterator();
			break;
		case SLOW:
			_iterator = new SlowSort(_numOfBars, _numToSort).sortAlgorithm().iterator();
			break;
		case THREEWAY_QUICK:
			_iterator = new ThreeWayQuickSort(_numOfBars, _numToSort).sortAlgorithm().iterator();
			break;
		case INTRO:
			_iterator = new IntroSort(_numOfBars, _numToSort).sortAlgorithm().iterator();
			break;
		case BOGO:
			if (_numOfBars > 9) {
				this.setUpAlert(AlgorithmMode.BOGO);
			} else {
				_iterator = new BogoSort(_numOfBars, _numToSort).sortAlgorithm().iterator();
			}
			break;
		case STOOGE:
			_iterator = new StoogeSort(_numOfBars, _numToSort).sortAlgorithm().iterator();
			break;
		case BOZO:
			if (_numOfBars > 9) {
				this.setUpAlert(AlgorithmMode.BOZO);
			} else {
				_iterator = new BozoSort(_numOfBars, _numToSort).sortAlgorithm().iterator();
			}
			break;
		case SELECTION:
			_iterator = new SelectionSort(_numOfBars, _numToSort).sortAlgorithm().iterator();
			break;
		case PIGEONHOLE:
			_isComparisonBased = false;
			_iteratorForNonComparison = new PigeonholeSort(_numOfBars, _numToSort, true).sortAlgorithm().iterator();
			break;
		case MERGE:
			_isComparisonBased = false;
			_iteratorForNonComparison = new MergeSort(_numOfBars, _numToSort, true).sortAlgorithm().iterator();
			break;
		case LSD_RADIX:
			_isComparisonBased = false;
			_iteratorForNonComparison = new LSDRadixSort(_numOfBars, _numToSort, true).sortAlgorithm().iterator();
			break;
		case AMERICAN_FLAG:
			_isComparisonBased = false;
			_iteratorForNonComparison = new AmericanFlagSort(_numOfBars, _numToSort, true).sortAlgorithm().iterator();
			break;
		case BUCKET:
			_isComparisonBased = false;
			_iteratorForNonComparison = new BucketSort(_numOfBars, _numToSort, true).sortAlgorithm().iterator();
			break;
		case TIM:
			_isComparisonBased = false;
			_iteratorForNonComparison = new TimSort(_numOfBars, _numToSort, true).sortAlgorithm().iterator();
			break;
		case THREEWAY_MERGE:
			_isComparisonBased = false;
			_iteratorForNonComparison = new ThreewayMergeSort(_numOfBars, _numToSort, true).sortAlgorithm().iterator();
			break;
		case ODD_EVEN:
			_iterator = new OddEvenSort(_numOfBars, _numToSort).sortAlgorithm().iterator();
			break;
		case HYBRID_QUICK:
			_isComparisonBased = false;
			_iteratorForNonComparison = new HybridQuickSort(_numOfBars, _numToSort).sortAlgorithm().iterator();
			break;
		case CYCLE:
			_isComparisonBased = false;
			_iteratorForNonComparison = new CycleSort(_numOfBars, _numToSort).sortAlgorithm().iterator();
			break;
		case STRAND:
			_isComparisonBased = false;
			_iteratorForNonComparison = new StrandSort(_numOfBars, _numToSort, true).sortAlgorithm().iterator();
			break;
		case BATCHER_ODD_EVEN_MERGE:
			try {
				_iterator = new OddEvenMergeSort(_numOfBars, _numToSort).sortAlgorithm().iterator();
			} catch (InvalidInputException e) {
			} catch (NotPowerOfTwoException e) {
				this.setUpAlertForBatcher();
			}
			break;
		case BITONIC:
			try {
				_iterator = new BitonicSort(_numOfBars, _numToSort).sortAlgorithm().iterator();
			} catch (InvalidInputException e) {
			} catch (NotPowerOfTwoException e) {
				this.setUpAlertForBatcher();
			}
		default:
			break;
		}
	}

	public static void setMode(AlgorithmMode mode) {
		_mode = mode;
		System.out.println(_mode.toString() + "selected");
	}

	private class KeyHandler implements EventHandler<KeyEvent> {
		/*
		 * This is the keyHandler. It allows user input during the game. User can quit
		 * via pressing Q or going to options.
		 */
		@Override
		@SuppressWarnings("static-access")
		public void handle(KeyEvent e) {
			KeyCode keyPressed = e.getCode();
			switch (keyPressed) {
			case Q: // allows user to quit via key input if necessary.
				Alert quitAlert = new Alert(AlertType.CONFIRMATION);
				quitAlert.setTitle("QUIT?");
				quitAlert.setContentText("Are you sure you want to quit?");
				quitAlert.setHeaderText(null);
				quitAlert.showAndWait();
				if (quitAlert.getResult() == ButtonType.OK) {
					Alert quitAlert2 = new Alert(AlertType.INFORMATION);
					quitAlert2.setTitle("THANK YOU");
					quitAlert2.setContentText("THANK YOU FOR VIEWING MY SORTING VISUALIZER!\n");
					quitAlert2.setHeaderText(null);
					quitAlert2.showAndWait();
					;
					System.exit(0);
				}
				break;
			case S:
				if (_timelineInitialized == false && _mode != _mode.NULL) {
					try {
						Sort.this.checkAlgorithm();
					} catch (InvalidInputException e1) {
						System.out.println("Invalid Input Exception found");
					}
					if (_mode == _mode.NULL) {
						break;
					}
					_timelineInitialized = true;
					if (_isComparisonBased) {
						Sort.this.setUpTimeline();
					} else {
						Sort.this.setUpTimelineForNonComparison();
					}
				}
				break;
			case C:
				if (_timelineInitialized == true) {
					if (_timeline.getStatus() == Status.RUNNING) {
						_timeline.pause();
						_pauseCount++;
					}
				}
				Alert quitAlert3 = new Alert(AlertType.CONFIRMATION);
				quitAlert3.setTitle("CLEAR?");
				quitAlert3.setContentText("Are you sure you want to clear the graph?");
				quitAlert3.setHeaderText(null);
				quitAlert3.showAndWait();
				if (quitAlert3.getResult() == ButtonType.OK) {
					Sort.this.clearGraph();
				}
			case P:
				if (_timelineInitialized == true) {
					_isPaused = !_isPaused;
					if (_pauseCount % 2 == 0) {
						_timeline.pause();
					} else if (_pauseCount % 2 != 0) {
						_timeline.play();
					}
					_pauseCount++;
				}

				break;
			default:
				break;
			}
			e.consume();
		}
	}
}
