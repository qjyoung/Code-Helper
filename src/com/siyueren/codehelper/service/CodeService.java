package com.siyueren.codehelper.service;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import org.jetbrains.annotations.NotNull;

/**
 * @author 乔健勇
 * @date 23:12 2020/1/5
 * @email qjyoung@163.com
 */
public class CodeService {
    
    private Project project;
    private static final String LINE_SEPARATOR = "\n";
    
    public CodeService(Project project) {
        this.project = project;
    }
    
    public static CodeService getInstance(@NotNull Project project) {
        return ServiceManager.getService(project, CodeService.class);
    }
    
    public void addCode(Editor editor, @NotNull PsiElement element, PsiField[] fields) {
        final Document myDocument = editor.getDocument();
        PsiElementFactory elementFactory = JavaPsiFacade.getInstance(project).getElementFactory();
        PsiDocumentManager psiDocumentManager = PsiDocumentManager.getInstance(project);
        final int textOffset = element.getTextOffset();
        final String theVar = element.getText();
        element.delete();
        StringBuilder sb = new StringBuilder();
        try {
            for (int index = 0; index < fields.length; index++) {
                PsiField code = fields[index];
                final String name = code.getName();
                String exp = theVar + ".set" + name.substring(0, 1).toUpperCase() + name.substring(1) + "();\n";
                System.out.println(exp);
                sb.append(exp);
            }
            myDocument.insertString(textOffset, sb.toString());
            psiDocumentManager.doPostponedOperationsAndUnblockDocument(myDocument);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        JavaCodeStyleManager.getInstance(project).shortenClassReferences(element.getParent());
    }
}