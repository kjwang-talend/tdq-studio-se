// ============================================================================
//
// Copyright (C) 2006-2013 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.dataprofiler.core;

/**
 * DOC Administrator class global comment. Detailled comment
 * 
 * @deprecated use org.talend.commons.utils.platform.PluginChecker instead
 */
public final class PluginChecker {

    private static final String TDQ_ID = "org.talend.dataprofiler.core.tdq"; //$NON-NLS-1$

    private static final String TDCP_ID = "org.talend.datacleansing.core"; //$NON-NLS-1$

    private PluginChecker() {
    }

    /**
     * Check if specific plug-in is loaded.
     * 
     * @return isLoaded
     */
    public static boolean isPluginLoaded(String pluginID) {
        return org.talend.commons.utils.platform.PluginChecker.isPluginLoaded(pluginID);
    }

    /**
     * DOC bZhou Comment method "isTDQLoaded".
     * 
     * use it to test if the platform start by TDQ.
     * 
     * @return
     */
    public static boolean isTDQLoaded() {
        return isPluginLoaded(TDQ_ID);
    }

    /**
     * DOC bZhou Comment method "isTDCPLoaded".
     * 
     * use it to test if the platform start by TDCP.
     * 
     * @return
     */
    public static boolean isTDCPLoaded() {
        return isPluginLoaded(TDCP_ID);
    }
}
