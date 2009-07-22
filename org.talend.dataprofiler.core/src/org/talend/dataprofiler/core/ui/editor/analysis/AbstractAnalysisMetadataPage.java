// ============================================================================
//
// Copyright (C) 2006-2009 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.dataprofiler.core.ui.editor.analysis;

import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.MessageDialogWithToggle;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.part.FileEditorInput;
import org.talend.cwm.softwaredeployment.TdDataProvider;
import org.talend.dataprofiler.core.i18n.internal.DefaultMessagesImpl;
import org.talend.dataprofiler.core.ui.IRuningStatusListener;
import org.talend.dataprofiler.core.ui.editor.AbstractMetadataFormPage;
import org.talend.dataquality.PluginConstant;
import org.talend.dataquality.analysis.Analysis;
import org.talend.dataquality.exception.DataprofilerCoreException;
import org.talend.dataquality.exception.ExceptionHandler;
import org.talend.dq.helper.resourcehelper.AnaResourceFileHelper;
import org.talend.dq.helper.resourcehelper.PrvResourceFileHelper;
import org.talend.resource.ResourceManager;
import org.talend.utils.sugars.ReturnCode;
import orgomg.cwm.objectmodel.core.ModelElement;

/**
 * DOC rli class global comment. Detailled comment
 */
public abstract class AbstractAnalysisMetadataPage extends
		AbstractMetadataFormPage implements IRuningStatusListener {

    protected static Logger log = Logger
			.getLogger(AbstractAnalysisMetadataPage.class);

	protected Analysis analysis;

	protected AnalysisEditor currentEditor = null;
	protected CCombo connCombo;

	public CCombo getConnCombo() {
		return connCombo;
	}

	public Analysis getAnalysis() {
		return analysis;
	}

	public AbstractAnalysisMetadataPage(FormEditor editor, String id,
			String title) {
		super(editor, id, title);
		currentEditor = (AnalysisEditor) editor;
	}

	@Override
	protected ModelElement getCurrentModelElement(FormEditor editor) {
		FileEditorInput input = (FileEditorInput) editor.getEditorInput();
		analysis = AnaResourceFileHelper.getInstance().findAnalysis(
				input.getFile());
		return analysis;
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		ReturnCode rc = canSave();
		if (!rc.isOk()) {
			MessageDialogWithToggle
					.openError(
							null,
							DefaultMessagesImpl
									.getString("AbstractAnalysisMetadataPage.SaveAnalysis"), rc.getMessage()); //$NON-NLS-1$
		} else {
			super.doSave(monitor);
			try {
				saveAnalysis();
				this.isDirty = false;
			} catch (DataprofilerCoreException e) {
				ExceptionHandler.process(e, Level.ERROR);
				log.error(e, e);
			}
		}
	}
	
	public ScrolledForm getScrolledForm(){
	    return null;
	}

	protected abstract ReturnCode canSave();

	protected abstract ReturnCode canRun();

	public abstract void refresh();

	protected abstract void saveAnalysis() throws DataprofilerCoreException;

	public void setDirty(boolean isDirty) {
		if (this.isDirty != isDirty) {
			this.isDirty = isDirty;
			((AnalysisEditor) this.getEditor())
					.firePropertyChange(IEditorPart.PROP_DIRTY);
		}
	}

	public void fireRuningItemChanged(boolean status) {
		currentEditor.setRunActionButtonState(status);
		currentEditor.setRefreshResultPage(status);

		if (status) {
			refresh();
		}
	}

	/**
	 * 
	 * MOD mzhao 2009-06-17 feature 5887
	 * 
	 * @param parentComp
	 */
	public void createConnBindWidget(Composite parentComp) {
		// ~ MOD mzhao 2009-05-05,Bug 6587.
		Composite labelButtonClient = toolkit.createComposite(parentComp);
		GridLayout labelButtonClientLayout = new GridLayout();
		labelButtonClientLayout.numColumns = 2;
		labelButtonClient.setLayout(labelButtonClientLayout);

		toolkit.createLabel(labelButtonClient, DefaultMessagesImpl
				.getString("AbstractMetadataFormPage.connBind")); //$NON-NLS-1$
		connCombo = new CCombo(labelButtonClient, SWT.BORDER);
		connCombo.setEditable(false);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.TOP).applyTo(
				labelButtonClient);
		reloadDataproviderAndFillConnCombo();
		// ~

	}

	// MOD mzhao 2009-05-05, bug 6587.
	protected void reloadDataproviderAndFillConnCombo() {
		IFolder connFolder = ResourceManager.getMetadataFolder().getFolder(
				PluginConstant.DB_CONNECTIONS);
		List<TdDataProvider> dataProviders = PrvResourceFileHelper
				.getInstance().getAllDataProviders(connFolder);

		int index = 0;
		connCombo.removeAll();
		for (TdDataProvider prov : dataProviders) {
			connCombo.add(prov.getName(), index);
			String prvFileName = PrvResourceFileHelper.getInstance()
					.findCorrespondingFile(prov).getName();
			connCombo.setData(prvFileName, index);
			connCombo.setData(index + "", prov);
			index++;
		}
		if (index > 0) {
			connCombo.select(0);
		}
	}
}
