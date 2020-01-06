package com.siyueren.codehelper.intention;

import com.intellij.codeInsight.intention.IntentionAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.psi.*;
import com.intellij.psi.impl.source.PsiClassReferenceType;
import com.intellij.psi.impl.source.tree.java.PsiIdentifierImpl;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IncorrectOperationException;
import com.siyueren.codehelper.service.CodeService;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        final PsiExpression psiExpression = PsiTreeUtil.getParentOfType(element, PsiExpression.class);
        final PsiType type = psiExpression.getType();
        
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
        CodeService codeService = CodeService.getInstance(project);
        PsiElement element = file.findElementAt(editor.getCaretModel().getOffset());
        System.out.println(element);
//        PsiParameter parameter = PsiTreeUtil.getParentOfType(element, PsiParameter.class);
//        codeService.addCode(parameter);
        
        final PsiExpression psiExpression = PsiTreeUtil.getParentOfType(element, PsiExpression.class);
        System.out.println(psiExpression);
        final PsiType type = psiExpression.getType();
        System.out.println(type);
        
        if (type instanceof PsiClassReferenceType && !((PsiClassReferenceType) type).hasParameters()) {
            PsiClass clazz = ((PsiClassReferenceType) type).resolve();
            System.out.println(clazz);
            final PsiField[] fields = clazz.getAllFields();
            for (PsiField field : fields) {
                final String name = field.getName();
                System.out.println(name);
            }
            final String fieldsString = Arrays.toString(fields);
            System.out.println(fieldsString);
//            element.delete();
            codeService.addCode(element, fields);
        }
        
        final Class<? extends PsiElement> aClass = element.getClass();
        System.out.println(aClass);
        PsiIdentifierImpl psiIdentifier = (PsiIdentifierImpl) element;
        System.out.println(psiIdentifier);
        
        final IElementType elementType = psiIdentifier.getElementType();
        System.out.println(elementType);
        final PsiReference[] references = psiIdentifier.getReferences();
        System.out.println(references);
//        final PsiReference reference = references[0];
//        System.out.println(reference);

//        final PsiClass psi = psiIdentifier.getPsi(PsiClass.class);
//        System.out.println(psi);
        final PsiElement parent = psiIdentifier.getParent();
        System.out.println(parent);
        final PsiReference reference = parent.getReference();
        System.out.println(reference);
        
        
        PsiClass clazz = PsiTreeUtil.getParentOfType(element, PsiClass.class);
        System.out.println(clazz);
        
        final PsiField[] fields = clazz.getAllFields();
        final List<PsiField> settableFields = new ArrayList<>(fields.length);
        System.out.println(settableFields);
        
    }
}