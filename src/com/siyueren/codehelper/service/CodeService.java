package com.siyueren.codehelper.service;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
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
        PsiElementFactory elementFactory = JavaPsiFacade.getInstance(project).getElementFactory();
        PsiDocumentManager psiDocumentManager = PsiDocumentManager.getInstance(project);
        
        final String lineIndent = codeStyleManager.getLineIndent(file, element.getTextOffset());
        final int lineIndentLength = lineIndent.length();
        try {
            int lineBreakOffset = 0;
            int[] positions = new int[fields.length];
            final String text = element.getText();
            for (int index = 0; index < fields.length; index++) {
                PsiField code = fields[index];
                final String name = code.getName();
                String exp = text + ".set" + name.substring(0, 1).toUpperCase() + name.substring(1) + "();";
                System.out.println(exp);
                PsiStatement another = elementFactory.createStatementFromText(exp, element.getParent());
                if (index == 0) {
                    element = element.replace(another);
                    lineBreakOffset = element.getTextRange().getEndOffset();
                } else {
                    element.add(another);
                    lineBreakOffset += another.getTextRange().getEndOffset();
                }
                positions[index] = lineBreakOffset;
            }
            psiDocumentManager.doPostponedOperationsAndUnblockDocument(myDocument);
            for (int line = 0; line < positions.length; ++line) {
                int position = positions[line] + line * (1 + lineIndentLength);
                myDocument.insertString(position, LINE_SEPARATOR + lineIndent);
                PsiDocumentManager.getInstance(project).commitAllDocuments();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}