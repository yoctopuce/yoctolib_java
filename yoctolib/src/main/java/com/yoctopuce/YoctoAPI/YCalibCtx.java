package com.yoctopuce.YoctoAPI;

import java.util.ArrayList;

public class YCalibCtx
{
    public String src;
    public int typ;
    public ArrayList<Integer> par;
    public ArrayList<Double> raw;
    public ArrayList<Double> cal;
    public YAPI.CalibrationHandlerCallback hdl;

    public YCalibCtx()
    {
        this.src = "";
        this.hdl = null;
        this.typ = 0;
        this.par = new ArrayList<>();
        this.raw = new ArrayList<>();
        this.cal = new ArrayList<>();

    }

    public YCalibCtx(String src, YAPI.CalibrationHandlerCallback hdl, int typ, ArrayList<Integer> par, ArrayList<Double> raw, ArrayList<Double> ref)
    {
        this.src = src;
        this.hdl = hdl;
        this.typ = typ;
        this.par = par;
        this.raw = raw;
        this.cal = ref;
    }
}
