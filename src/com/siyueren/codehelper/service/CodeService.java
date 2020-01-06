package com.siyueren.codehelper.service;

import com.intellij.openapi.components.ServiceManager;
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
    
    public CodeService(Project project) {
        this.project = project;
    }
    
    public static CodeService getInstance(@NotNull Project project) {
        return ServiceManager.getService(project, CodeService.class);
    }
    
    public void addCode(@NotNull PsiElement element, PsiField[] codes) {
        PsiElementFactory elementFactory = JavaPsiFacade.getInstance(project).getElementFactory();
        try {
            
            final String text = element.getText();
            final int textOffset = element.getTextOffset();
//            final PsiExpression expressionFromText = elementFactory.createExpressionFromText("user.setName(\"\");", element);
//            final PsiStatement statementFromText = elementFactory.createStatementFromText("user.seta();", element);
//            final PsiCodeBlock codeBlockFromText = elementFactory.createCodeBlockFromText("user.setName()", element);
//            final PsiComment commentFromText = elementFactory.createCommentFromText("// abc", element);
//            element.add(expressionFromText);
            int index = 1;
            for (PsiField code : codes) {
                final String name = code.getName();
                String exp = text + ".set" + name.substring(0, 1).toUpperCase() + name.substring(1) + "();";
                System.out.println(exp);
                PsiStatement another = elementFactory.createStatementFromText(exp, element);
                if (index == 1) {
                    element = element.getParent().replace(another);
                } else {
                    element.add(another);
                }
                index++;
            }
//            final PsiElement replace = element.getParent().replace(statementFromText);
//            final PsiStatement statementFromText2 = elementFactory.createStatementFromText("user.setb();", statementFromText);
//            replace.addAfter(replace, statementFromText2);
        } catch (Exception e
        ) {
            e.printStackTrace();
        }
        
        JavaCodeStyleManager.getInstance(project).shortenClassReferences(element.getParent());
    }
    
    public void addCode(@NotNull PsiModifierListOwner parameter) {
        PsiElementFactory elementFactory = JavaPsiFacade.getInstance(project).getElementFactory();
        try {
            PsiModifierList modifierList = parameter.getModifierList();
            PsiAnnotation psiAnnotation = elementFactory.createAnnotationFromText("@NotNull ", parameter);
            modifierList.add(psiAnnotation);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}