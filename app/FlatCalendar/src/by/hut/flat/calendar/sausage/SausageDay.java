package by.hut.flat.calendar.sausage;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import by.hut.flat.calendar.R;
import by.hut.flat.calendar.cell.SausageCellDay;
import by.hut.flat.calendar.core.Config;
import by.hut.flat.calendar.utils.CalendarHelper;
import by.hut.flat.calendar.utils.Date;

public class SausageDay extends LinearLayout implements ISausage{
	
	protected Context context;
	protected SausageCellDay[] cells;
	private Date startDate;
	private Date finishDate;
	private int cellsNum;
	private int FlatID;
	
	/* tmp Views */
	private SausageCellDay cell;
	
	protected boolean invariant(){
		return this.context != null
				&& cells != null
				&& cells.length > 0
				&& cells.length == this.cellsNum
				&& startDate != null
				&& finishDate != null
				&& FlatID >= 0;
	}
	/*------------------------------------------------------------
	-------------------- C O N S T R U C T O R S -----------------
	------------------------------------------------------------*/
	public SausageDay(Context context,Date startDate,Date finishDate){
		super(context);
		assert startDate != null;
		assert finishDate != null;
		assert startDate.compareTo(finishDate) == -1;
		assert startDate.daysUntil(finishDate) > 0;
		
		this.context = context;
		this.startDate = startDate;
		this.finishDate = finishDate;
		this.cellsNum = startDate.daysUntil(finishDate)+1;
		initView();
	}
	/*------------------------------------------------------------
	---------------------------- I N I T -------------------------
	------------------------------------------------------------*/
	private void initView(){
		LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.sausage_calendar_line, this);
        inflater = null;
	}
	
	/*------------------------------------------------------------
	----------------------- I N T E R F A C E --------------------
	------------------------------------------------------------*/
	/**
	 * Uses {@code Runnable} for long parsing and add cell init
	 */
	@Override
	public void initCells() {
		cells = new SausageCellDay[cellsNum];
		Date date = new Date(startDate.day,startDate.month,startDate.year);
		CalendarHelper calendar = new CalendarHelper(date);
		int dayOfWeek = calendar.dayOfWeek;
		/* creating and drawing cells */
		for (int i = 0; i < cellsNum; i++){
			if (dayOfWeek > 6) dayOfWeek = 0;
			cell = new SausageCellDay(context,date.copy(),(i == 0) ? true : false, (i == cellsNum-1) ? true : false,dayOfWeek);
			initCell(cell,i);
			date.next();
			dayOfWeek++;
			cell = null;
		}
		
		dayOfWeek = 0;
		calendar = null;
		date = null;
		
		assert invariant();
	}
	private void initCell(SausageCellDay cell, int pos){
		assert cell != null;
		cells[pos] = cell;
	}
	/**
	 * Adds cells to the layout
	 */
	@Override
	public void addCells(){
		for (int i = 0; i < cellsNum; i++){
			addView(cells[i]);
		}
	}
	
	public Date getDate(int scrollX){
		return cells[scrollX / Config.INST.SAUSAGE.CELL_WIDTH].date;
	}
}
