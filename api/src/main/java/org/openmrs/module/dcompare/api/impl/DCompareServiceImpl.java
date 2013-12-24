/**
 * The contents of this file are subject to the OpenMRS Public License
 * Version 1.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://license.openmrs.org
 *
 * Software distributed under the License is distributed on an "AS IS"
 * basis, WITHOUT WARRANTY OF ANY KIND, either express or implied. See the
 * License for the specific language governing rights and limitations
 * under the License.
 *
 * Copyright (C) OpenMRS, LLC.  All Rights Reserved.
 */
package org.openmrs.module.dcompare.api.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openmrs.Concept;
import org.openmrs.api.APIException;
import org.openmrs.api.context.Context;
import org.openmrs.api.impl.BaseOpenmrsService;
import org.openmrs.module.dcompare.api.DCompareService;
import org.openmrs.module.dcompare.api.db.DCompareDAO;
import org.openmrs.module.metadatasharing.MetadataSharing;
import org.openmrs.module.metadatasharing.api.MetadataSharingService;
import org.openmrs.module.metadatasharing.wrapper.PackageExporter;

/**
 * It is a default implementation of {@link DCompareService}.
 */
public class DCompareServiceImpl extends BaseOpenmrsService implements DCompareService {
	
	protected final Log log = LogFactory.getLog(this.getClass());
	
	private DCompareDAO dao;
	
	/**
     * @param dao the dao to set
     */
    public void setDao(DCompareDAO dao) {
	    this.dao = dao;
    }
    
    /**
     * @return the dao
     */
    public DCompareDAO getDao() {
	    return dao;
    }

	@Override
	public List<Concept> exportPackageWithConcepts(List<Concept> concepts) throws APIException {
		  PackageExporter exporter = MetadataSharing.getInstance().newPackageExporter();
		  
			for (Concept concept : concepts) {
				exporter.addItem(concept);
			}
			exporter.getPackage().setName("DcomparePackage");
			exporter.getPackage().setDescription("Metadata package created by dcompare module");
			exporter.exportPackage();
			
			Context.getService(MetadataSharingService.class).saveExportedPackage(exporter.getExportedPackage());
			
		return concepts;
	}
}