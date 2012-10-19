package edu.x3m.kas.core.iface;


import edu.x3m.kas.monitors.IHuffmannMonitor;


/**
 *
 * @author Hans
 */
public interface IProgressable {


    /**
     * Method sets new monitor.
     *
     * @param monitor
     */
    void setHuffmannMonitor (IHuffmannMonitor monitor);



    /**
     * Method returns current monitor.
     *
     * @return current monitor
     */
    IHuffmannMonitor getHuffmannMonitor ();
}
