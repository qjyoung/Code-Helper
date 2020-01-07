package com.siyueren.codehelper.service;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiFile;
import com.intellij.psi.codeStyle.CodeStyleManager;
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
    
    public void addCode(PsiFile file, Editor editor, @NotNull PsiElement element, PsiField[] fields) {
        final Document myDocument = editor.getDocument();
        CodeStyleManager codeStyleManager = CodeStyleManager.getInstance(project);
        PsiDocumentManager psiDocumentManager = PsiDocumentManager.getInstance(project);
        
        final int elementOffset = element.getTextOffset();
        final String lineIndent = codeStyleManager.getLineIndent(file, elementOffset);
        
        try {
            StringBuilder sb = new StringBuilder();
            final String text = element.getText();
            for (int index = 0; index < fields.length; index++) {
                PsiField code = fields[index];
                final String name = code.getName();
                String exp;
                if (index == 0) {
                    exp = ".set" + name.substring(0, 1).toUpperCase() + name.substring(1) + "();";
                } else {
                    exp = text + ".set" + name.substring(0, 1).toUpperCase() + name.substring(1) + "();";
                }
                sb.append(exp);
                if (index != fields.length - 1) {
                    sb.append(LINE_SEPARATOR + lineIndent);
                }
            }
            myDocument.insertString(element.getTextRange().getEndOffset(), sb.toString());
            psiDocumentManager.doPostponedOperationsAndUnblockDocument(myDocument);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}