package com.siyueren.codehelper.intention;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.PsiClassReferenceType;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IncorrectOperationException;
import com.siyueren.codehelper.service.CodeService;
import org.jetbrains.annotations.NotNull;

/**
 * @author 乔健勇
 * @date 20:28 2020/1/5
 * @email qjyoung@163.com
 */
public class GenerateSetterCallsIntention implements IntentionAction {
    
    @NotNull
    @Override
    public String getFamilyName() {
        return getText();
    }
    
    @Override
    public boolean isAvailable(@NotNull Project project, Editor editor, PsiFile file) {
        PsiElement element = file.findElementAt(editor.getCaretModel().getOffset());
        if (element == null) {
            return false;
        }
        final PsiExpression psiExpression = PsiTreeUtil.getParentOfType(element, PsiExpression.class);
        if (psiExpression == null) {
            return false;
        }
        final PsiType type = psiExpression.getType();
        if (type == null) {
            return false;
        }
        
        return type instanceof PsiClassReferenceType;
    }
    
    @Override
    public boolean startInWriteAction() {
        return true;
    }
    
    @NotNull
    @Override
    public String getText() {
        return "[Code-Helper] Generate Setter Calls";
    }
    
    @Override
    public void invoke(@NotNull Project project, Editor editor, PsiFile file) throws IncorrectOperationException {
        PsiElement element = file.findElementAt(editor.getCaretModel().getOffset());
        
        final PsiExpression psiExpression = PsiTreeUtil.getParentOfType(element, PsiExpression.class);
        final PsiType type = psiExpression.getType();
        if (type instanceof PsiClassReferenceType) {
            PsiClass clazz = ((PsiClassReferenceType) type).resolve();
            System.out.println(clazz);
            final PsiField[] fields = clazz.getAllFields();
            CodeService.getInstance(project).addCode(file, editor, element, fields);
        }
    }
}