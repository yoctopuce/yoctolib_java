/*********************************************************************
 *
 * $Id: YCellRecord.java 38239 2019-11-20 11:36:26Z seb $
 *
 * Implements FindCellRecord(), the high-level API for CellRecord functions
 *
 * - - - - - - - - - License information: - - - - - - - - -
 *
 *  Copyright (C) 2011 and beyond by Yoctopuce Sarl, Switzerland.
 *
 *  Yoctopuce Sarl (hereafter Licensor) grants to you a perpetual
 *  non-exclusive license to use, modify, copy and integrate this
 *  file into your software for the sole purpose of interfacing
 *  with Yoctopuce products.
 *
 *  You may reproduce and distribute copies of this file in
 *  source or object form, as long as the sole purpose of this
 *  code is to interface with Yoctopuce products. You must retain
 *  this notice in the distributed source file.
 *
 *  You should refer to Yoctopuce General Terms and Conditions
 *  for additional information regarding your rights and
 *  obligations.
 *
 *  THE SOFTWARE AND DOCUMENTATION ARE PROVIDED 'AS IS' WITHOUT
 *  WARRANTY OF ANY KIND, EITHER EXPRESS OR IMPLIED, INCLUDING
 *  WITHOUT LIMITATION, ANY WARRANTY OF MERCHANTABILITY, FITNESS
 *  FOR A PARTICULAR PURPOSE, TITLE AND NON-INFRINGEMENT. IN NO
 *  EVENT SHALL LICENSOR BE LIABLE FOR ANY INCIDENTAL, SPECIAL,
 *  INDIRECT OR CONSEQUENTIAL DAMAGES, LOST PROFITS OR LOST DATA,
 *  COST OF PROCUREMENT OF SUBSTITUTE GOODS, TECHNOLOGY OR
 *  SERVICES, ANY CLAIMS BY THIRD PARTIES (INCLUDING BUT NOT
 *  LIMITED TO ANY DEFENSE THEREOF), ANY CLAIMS FOR INDEMNITY OR
 *  CONTRIBUTION, OR OTHER SIMILAR COSTS, WHETHER ASSERTED ON THE
 *  BASIS OF CONTRACT, TORT (INCLUDING NEGLIGENCE), BREACH OF
 *  WARRANTY, OR OTHERWISE.
 *
 *********************************************************************/

package com.yoctopuce.YoctoAPI;
//--- (generated code: YCellRecord return codes)
//--- (end of generated code: YCellRecord return codes)
//--- (generated code: YCellRecord class start)
/**
 * YCellRecord Class: Description of a cellular antenna
 *
 *
 */
@SuppressWarnings({"UnusedDeclaration", "UnusedAssignment"})
public class YCellRecord
{
//--- (end of generated code: YCellRecord class start)
//--- (generated code: YCellRecord definitions)
    protected String _oper;
    protected int _mcc = 0;
    protected int _mnc = 0;
    protected int _lac = 0;
    protected int _cid = 0;
    protected int _dbm = 0;
    protected int _tad = 0;

    //--- (end of generated code: YCellRecord definitions)

    public YCellRecord(int mcc,int mnc,int lac,int cellId,int dbm,int tad,String oper)
    {
        //--- (generated code: YCellRecord attributes initialization)
        //--- (end of generated code: YCellRecord attributes initialization)
        _oper = oper;
        _mcc = mcc;
        _mnc = mnc;
        _lac = lac;
        _cid = cellId;
        _dbm = dbm;
        _tad = tad;
    }

    //--- (generated code: YCellRecord implementation)

    /**
     * Returns the name of the the cell operator.
     *
     * @return a string with the name of the the cell operator.
     */
    public String get_cellOperator()
    {
        return _oper;
    }

    /**
     * Returns the Mobile Country Code (MCC).
     *
     * @return the Mobile Country Code (MCC).
     */
    public int get_mobileCountryCode()
    {
        return _mcc;
    }

    /**
     * Returns the Mobile Network Code (MNC).
     *
     * @return the Mobile Network Code (MNC).
     */
    public int get_mobileNetworkCode()
    {
        return _mnc;
    }

    /**
     * Returns the Location Area Code (LAC).
     *
     * @return the Location Area Code (LAC).
     */
    public int get_locationAreaCode()
    {
        return _lac;
    }

    /**
     * Returns the Cell Id.
     *
     * @return the Cell Id.
     */
    public int get_cellId()
    {
        return _cid;
    }

    /**
     * Returns the signal strength.
     *
     * @return the signal strength.
     */
    public int get_signalStrength()
    {
        return _dbm;
    }

    /**
     * Returns the Timing Advance (TA).
     *
     * @return the Timing Advance (TA).
     */
    public int get_timingAdvance()
    {
        return _tad;
    }

    //--- (end of generated code: YCellRecord implementation)
}

