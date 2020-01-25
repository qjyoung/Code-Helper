package com.siyueren.codehelper;

import com.intellij.ide.impl.DataManagerImpl;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.actionSystem.impl.ActionMenuItem;
import com.intellij.openapi.fileEditor.FileEditor;
import com.intellij.openapi.fileEditor.FileEditorLocation;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

/**
 * @author 乔健勇
 * @date 23:40 2019/7/27
 * @email qjyoung@163.com
 */
public class MakeNotes extends AnAction {
    
    @Override
    public void actionPerformed(AnActionEvent event) {
        System.out.println("MakeNotes.actionPerformed");
        System.out.println(event.getPlace());
        final DataManagerImpl.MyDataContext dataContext = (DataManagerImpl.MyDataContext) event.getDataContext();
        final FileEditor fileEditor = dataContext.getData(PlatformDataKeys.FILE_EDITOR);
        final FileEditorLocation currentLocation = fileEditor.getCurrentLocation();
        System.out.println(currentLocation.getEditor().getFile().getPath());
        
        final Object[] data = dataContext.getData(PlatformDataKeys.SELECTED_ITEMS);
        System.out.println(data);
        
        final ActionMenuItem source = (ActionMenuItem) event.getInputEvent().getSource();
        // TODO: insert action logic here
        Project project = event.getData(PlatformDataKeys.PROJECT);
        Messages.showInputDialog(
                project,
                "What is your name?",
                "Input Your Name",
                Messages.getQuestionIcon());
    }
}