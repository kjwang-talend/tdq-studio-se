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
package org.talend.dataprofiler.core.ui.grid;

import org.eclipse.nebula.widgets.grid.GridCellRenderer;
import org.eclipse.nebula.widgets.grid.GridItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;

/**
 * The renderer for a cell in Grid.
 */
public class TdCellRenderer extends GridCellRenderer {

    int leftMargin = 4;

    int rightMargin = 4;

    int topMargin = 0;

    int bottomMargin = 0;

    private int insideMargin = 3;

    /**
     * {@inheritDoc}
     */
    public void paint(GC gc, Object value) {
        GridItem item = (GridItem) value;

        int column = getColumn();
        boolean checkable = item.getCheckable(column);
        boolean checked = item.getChecked(column);

        // fill background rectangle
        Color systemBackColor = getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND);
        if (checkable) {
            gc.setBackground(getDisplay().getSystemColor(SWT.COLOR_LIST_BACKGROUND));
        } else {
            gc.setBackground(IndicatorSelectGrid.gray);
        }

        int originX = getBounds().x;
        gc.fillRectangle(originX, getBounds().y, getBounds().width, getBounds().height);

        if (checkable) {
            // draw highlight color as background
            Color highlight = item.getBackground(column);
            if (highlight != null) {
                gc.setBackground(highlight);
                gc.fillRectangle(originX, getBounds().y, getBounds().width, getBounds().height);
            }

            if (checked) {
                // draw background oval
                int offset = (getBounds().width - getBounds().height);
                gc.setBackground(IndicatorSelectGrid.blue);
                gc.fillOval(originX + offset / 2 + 1, getBounds().y + 1, getBounds().width - offset - 2, getBounds().height - 2);

                // draw a white oval for partially selected cells
                if (item.getGrayed(column)) {
                    gc.setBackground(systemBackColor);
                    gc.setAlpha(160);
                    gc.fillOval(originX + offset / 2 + 1, getBounds().y + 1, getBounds().width - offset - 2,
                            getBounds().height - 2);
                    gc.setAlpha(-1);
                }

                // draw tick image
                if (highlight != null) {
                    gc.setForeground(highlight);
                } else {
                    gc.setForeground(systemBackColor);
                }
                gc.setLineWidth(3);
                gc.drawLine(originX + 18, getBounds().y + 11, originX + 23, getBounds().y + 16);
                gc.drawLine(originX + 21, getBounds().y + 16, originX + 31, getBounds().y + 6);
                gc.setLineWidth(1);
            }
        }
        if (item.getParent().getLinesVisible()) {
            gc.setForeground(item.getParent().getLineColor());
            gc.drawLine(originX, getBounds().y + getBounds().height, originX + getBounds().width - 1, getBounds().y
                    + getBounds().height);
            gc.drawLine(originX + getBounds().width - 1, getBounds().y, originX + getBounds().width - 1, getBounds().y
                    + getBounds().height);
        }
    }

    /**
     * {@inheritDoc}
     */
    public Point computeSize(GC gc, int wHint, int hHint, Object value) {
        GridItem item = (GridItem) value;

        gc.setFont(item.getFont(getColumn()));

        int x = 0;

        x += leftMargin;

        if (isCheck()) {
            x += getBounds().width + insideMargin;
        }

        int y = 0;

        Image image = item.getImage(getColumn());
        if (image != null) {
            y = topMargin + image.getBounds().height + bottomMargin;

            x += image.getBounds().width + insideMargin;
        }

        return new Point(x, y);
    }

    /**
     * {@inheritDoc}
     */
    public boolean notify(int event, Point point, Object value) {
        return false;
    }

}