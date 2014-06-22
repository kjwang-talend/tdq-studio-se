// ============================================================================
//
// Copyright (C) 2006-2012 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.dq.nodes;

import java.util.ArrayList;
import java.util.List;

import org.talend.core.model.metadata.builder.connection.MetadataTable;
import org.talend.core.model.repository.IRepositoryViewObject;
import org.talend.core.repository.model.repositoryObject.MetadataTableRepositoryObject;
import org.talend.repository.model.IRepositoryNode;
import org.talend.repository.model.RepositoryNode;

/**
 * DOC qiongli class global comment. Detailled comment
 */
public class DFTableRepNode extends DQRepositoryNode {

    private MetadataTableRepositoryObject mdTableRepositoryObject;

    private MetadataTable metadataTable;
    /**
     * DOC qiongli DFRepositoryNode constructor comment.
     * 
     * @param object
     * @param parent
     * @param type
     */
    public DFTableRepNode(IRepositoryViewObject object, RepositoryNode parent, ENodeType type) {
        super(object, parent, type);
        this.mdTableRepositoryObject = (MetadataTableRepositoryObject) object;
        this.metadataTable = this.mdTableRepositoryObject.getTable();
    }

    @Override
    public List<IRepositoryNode> getChildren() {
        List<IRepositoryNode> nodes = new ArrayList<IRepositoryNode>();
        MetadataTableRepositoryObject viewObject = (MetadataTableRepositoryObject) this.getObject();
        DFColumnFolderRepNode columnFolderNode = new DFColumnFolderRepNode(viewObject, this, ENodeType.TDQ_REPOSITORY_ELEMENT);
        nodes.add(columnFolderNode);
        return filterResultsIfAny(nodes);
    }

    public MetadataTable getMetadataTable() {
        return this.metadataTable;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.model.RepositoryNode#getLabel()
     */
    @Override
    public String getLabel() {
        if (getObject() == null) {
            return this.getProperties(EProperties.LABEL).toString();
        }
        return this.getObject().getLabel();
    }

}