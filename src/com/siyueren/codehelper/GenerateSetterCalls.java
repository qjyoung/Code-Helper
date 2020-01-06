package com.siyueren.codehelper;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiUtilBase;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @author 乔健勇
 * @date 22:46 2020/1/4
 * @email qjyoung@163.com
 */
public class GenerateSetterCalls extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent anActionEvent) {
        Project project = anActionEvent.getProject();
        Editor editor = anActionEvent.getData(PlatformDataKeys.EDITOR);
        PsiFile psiFile = PsiUtilBase.getPsiFileInEditor(editor, project);
        String fileName = psiFile.getName();
        Document document = PsiDocumentManager.getInstance(project).getDocument(psiFile);
        DefaultMutableTreeNode fileNode = new DefaultMutableTreeNode(fileName);
        for (PsiElement psiElement : psiFile.getChildren()) {
            System.out.println(psiElement);
            
            if (psiElement instanceof PsiClass) {
                PsiClass psiClass = (PsiClass) psiElement;
                final PsiField[] allFields = psiClass.getAllFields();
            }
        }
    }
    
}