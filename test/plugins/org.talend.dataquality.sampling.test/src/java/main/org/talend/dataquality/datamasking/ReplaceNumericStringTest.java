// ============================================================================
//
// Copyright (C) 2006-2015 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.dataquality.datamasking;

import static org.junit.Assert.*;

import org.junit.Test;
import org.talend.dataquality.datamasking.Functions.ReplaceNumericString;

/**
 * created by jgonzalez on 25 juin 2015 Detailled comment
 *
 */
public class ReplaceNumericStringTest {

    private String input = "abc123def"; //$NON-NLS-1$

    private String output;

    private ReplaceNumericString rns = new ReplaceNumericString();

    @Test
    public void testGood() {
        rns.parameters = "0".split(","); //$NON-NLS-1$ //$NON-NLS-2$
        output = rns.generateMaskedRow(input);
        assertEquals(output, "abc000def"); //$NON-NLS-1$
    }

    @Test
    public void testBad() {
        rns.parameters = "0X".split(","); //$NON-NLS-1$ //$NON-NLS-2$
        output = rns.generateMaskedRow(input);
        assertEquals(output, "abcXXXdef"); //$NON-NLS-1$
    }
}