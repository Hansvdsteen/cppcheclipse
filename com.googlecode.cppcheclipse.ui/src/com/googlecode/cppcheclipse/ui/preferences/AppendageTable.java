package com.googlecode.cppcheclipse.ui.preferences;

import java.io.File;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;

import com.googlecode.cppcheclipse.core.Appendages;
import com.googlecode.cppcheclipse.ui.Messages;

public class AppendageTable extends TableEditor<Appendages, File> {

	private final IProject project;
	
	static enum TableColumn {
		Filename
	};

	public AppendageTable(String name, String labelText, Composite parent,
			IProject project) {
		super(name, labelText, parent);

		getTableViewer(parent).getTable().setHeaderVisible(true);
		getTableViewer(parent).getTable().setLinesVisible(true);
		addColumn(new ExtendedTableColumn(Messages.AppendageTable_ColumnFile, SWT.LEFT, 150));

		getTableViewer(parent).setLabelProvider(new LabelProvider());
		this.project = project;
	}

	private static class LabelProvider implements ITableLabelProvider {

		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {
			String text = ""; //$NON-NLS-1$
			TableColumn column = TableColumn.values()[columnIndex];
			switch (column) {
			case Filename:
				text = ((File) element).toString();
				break;
			}
			return text;
		}

		public void addListener(ILabelProviderListener listener) {
		}

		public void dispose() {
		}

		public boolean isLabelProperty(Object element, String property) {
			return false;
		}

		public void removeListener(ILabelProviderListener listener) {
		}

	}

	@Override
	protected void createButtons(Composite box) {
		createPushButton(box, Messages.TableEditor_Add, new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				addPressed();
			}
		});
		createPushButton(box, Messages.TableEditor_AddExternal,
				new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						addExternalPressed();
					}
				});
		createPushButton(box, Messages.TableEditor_Remove,
				new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						removePressed();
					}
				});
		createPushButton(box, Messages.TableEditor_RemoveAll,
				new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						removeAllPressed();
					}
				});
	}

	protected void addPressed() {
		IResource resource = openProjectFile(Messages.AppendageTable_FileSelection, Messages.AppendageTable_FileSelectionMessage, project, false);
		if (resource != null) {
			File file = resource.getProjectRelativePath().toFile();
			getModel().add(file);
			getTableViewer().add(file);
		}
	}

	protected void addExternalPressed() {
		File file = openExternalFile(Messages.AppendageTable_FileSelection);
		if (file != null) {
			getModel().add(file);
			getTableViewer().add(file);
		}
	}

	@Override
	protected Appendages createModel() {
		return new Appendages(getPreferenceStore());
	}
}
