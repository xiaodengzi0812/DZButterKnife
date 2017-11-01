/*
* 最终要生成的代码如下
// ButterKnife自动生成，请勿随便修改！
package com.annotations.dzbutterknife;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.view.View.OnClickListener;
import com.dengzi.mybutterknife.Unbinder;
import com.dengzi.mybutterknife.Utils;
import java.lang.Override;

public final class MainActivity_ViewBinding implements Unbinder {
  private MainActivity mTarget;

  @UiThread
  public MainActivity_ViewBinding(MainActivity target) {
    this.mTarget = target;
    mTarget.tv1 = Utils.findViewById(target,2131427415);
    mTarget.tv2 = Utils.findViewById(target,2131427416);
    Utils.findViewById(target,2131427415).setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View view) {
        mTarget.onClick(view);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    MainActivity target = this.mTarget;
    if (target == null) return;
    this.mTarget = null;
  }
}
* */

package com.dengzi.compiler;

import com.dengzi.annotations.BindClick;
import com.dengzi.annotations.BindView;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

/**
 * @author Djk
 * @Title: apt自动生成代码
 * @Time: 2017/10/31.
 * @Version:1.0.0
 */
@AutoService(Processor.class)
public class MyButterKnifeProcessor extends AbstractProcessor {
    private Filer mFiler;
    private Elements mElementUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        mFiler = processingEnvironment.getFiler();
        mElementUtils = processingEnvironment.getElementUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        // 获取点击view的属性
        Set<? extends Element> bindClickElements = roundEnvironment.getElementsAnnotatedWith(BindClick.class);
//        for (Element element : bindClickElements) {
//            Element enclosingElement = element.getEnclosingElement();
//            System.out.println("----" + enclosingElement.getSimpleName().toString() + "----" + element.getSimpleName().toString());
//            // ----MainActivity----onClick
//        }
        // 获取绑定view的属性
        Set<? extends Element> bindViewElements = roundEnvironment.getElementsAnnotatedWith(BindView.class);
        /* 我们打印一下roundEnvironment这个里面到底有什么东西*/
//        for (Element element : bindViewElements) {
//            Element enclosingElement = element.getEnclosingElement();
//            System.out.println("----" + enclosingElement.getSimpleName().toString() + "----" + element.getSimpleName().toString());
//            // ----MainActivity----tv1
//            // ----MainActivity----tv2
//            // ----UserActivity----userTv1
//            // ----UserActivity----userTv2
//        }

        // 整合数据
        Map<Element, Map<Class, List<Element>>> elementMapMap = new LinkedHashMap<>();
        initData(bindViewElements, elementMapMap, BindView.class);
        initData(bindClickElements, elementMapMap, BindClick.class);

        // 生成 java 类
        for (Map.Entry<Element, Map<Class, List<Element>>> entry : elementMapMap.entrySet()) {
            // MainActivity
            Element enclosingElement = entry.getKey();
            // 拿到MainActivity字符串
            String classNameStr = enclosingElement.getSimpleName().toString();
            // 拿到MainActivity的ClassName
            ClassName parameterClassName = ClassName.bestGuess(classNameStr);

            // 生成类
            TypeSpec.Builder typeSpecBuilder = makeClazz(parameterClassName);
            // 生成unbind方法
            MethodSpec.Builder unbindMethodBuilder = makeUnbindMethod(parameterClassName);
            // 生成构造函数
            MethodSpec.Builder constructorBuilder = makeConstructor(parameterClassName);

            // MainActivity下对应的注解属性集合
            Map<Class, List<Element>> elementMap = entry.getValue();
            List<Element> elementList = elementMap.get(BindView.class);
            List<Element> bindClickElementList = elementMap.get(BindClick.class);
            // 添加 target.tv1 = Utils.findViewById(target,R.id.tv1);
            if (elementList != null) {
                for (Element bindViewElement : elementList) {
                    // 拿到属性名 tv1
                    String filedName = bindViewElement.getSimpleName().toString();
                    // 获取工具类名utils
                    ClassName utilClassName = ClassName.get("com.dengzi.mybutterknife", "Utils");
                    // 获取id值
                    int resId = bindViewElement.getAnnotation(BindView.class).value();
                    // target.tv1 = Utils.findViewById(target,2131427415); 生成这句代码并添加到构造函数中
                    constructorBuilder.addStatement("mTarget.$L = $T.findViewById(target,$L)", filedName, utilClassName, resId);
                }
            }
            // 添加点击事件
            if (bindClickElementList != null) {
                for (Element bindViewElement : bindClickElementList) {
                    // 拿到属性名 tv1
                    String filedName = bindViewElement.getSimpleName().toString();
                    // 获取工具类名utils
                    ClassName utilClassName = ClassName.get("com.dengzi.mybutterknife", "Utils");
                    // 获取id值
                    int[] resId = bindViewElement.getAnnotation(BindClick.class).value();
                    for (int id : resId) {
                        ClassName viewClickClassName = ClassName.get("android.view.View", "OnClickListener");
                        ClassName viewClassName = ClassName.bestGuess("android.view.View");
                        TypeSpec listener = TypeSpec.anonymousClassBuilder("")
                                .addSuperinterface(viewClickClassName)
                                .addMethod(MethodSpec.methodBuilder("onClick")
                                        .addAnnotation(Override.class)
                                        .addModifiers(Modifier.PUBLIC)
                                        .returns(TypeName.VOID)
                                        .addParameter(viewClassName, "view")
                                        .addStatement("mTarget.$L(view)", filedName)
                                        .build())
                                .build();
                        constructorBuilder.addStatement("$T.findViewById(target,$L).setOnClickListener($L)", utilClassName, id, listener);
                    }
                }
            }
            // 将构造函数添加到类中
            typeSpecBuilder.addMethod(constructorBuilder.build());
            // 将unbind方法添加到类中
            typeSpecBuilder.addMethod(unbindMethodBuilder.build());

            try {
                // 获取包名
                String packageName = mElementUtils.getPackageOf(enclosingElement).getQualifiedName().toString();
                // 写入生成 java 类
                JavaFile.builder(packageName, typeSpecBuilder.build())
                        .addFileComment("ButterKnife自动生成，请勿随便修改！")
                        .build().writeTo(mFiler);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 初始化数据
     *
     * @param bindViewElements
     * @param elementMapMap
     */
    private void initData(Set<? extends Element> bindViewElements, Map<Element, Map<Class, List<Element>>> elementMapMap, Class clazz) {
        for (Element element : bindViewElements) {
            Element enclosingElement = element.getEnclosingElement();
            Map<Class, List<Element>> elementMap = elementMapMap.get(enclosingElement);
            if (elementMap == null) {
                elementMap = new LinkedHashMap<>();
                List<Element> elementList = new ArrayList<>();
                elementMap.put(clazz, elementList);
                elementMapMap.put(enclosingElement, elementMap);
            }
            List<Element> elementList = elementMap.get(clazz);
            if (elementList == null) {
                elementList = new ArrayList<>();
                elementMap.put(clazz, elementList);
            }
            elementList.add(element);
        }
    }

    /**
     * 生成类
     *
     * @param parameterClassName
     * @return
     */
    private TypeSpec.Builder makeClazz(ClassName parameterClassName) {
        /* 生成 如下代码
            public final class XXX_ViewBinding implements Unbinder {
                private MainActivity mTarget;
            }
        */
        ClassName unbinderClassName = ClassName.get("com.dengzi.mybutterknife", "Unbinder");
        // 组装类:  public final class MainActivity_ViewBinding implements Unbinder{}
        TypeSpec.Builder typeSpecBuilder = TypeSpec.classBuilder(parameterClassName.simpleName() + "_ViewBinding")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addSuperinterface(unbinderClassName);
        // 添加属性 private MainActivity target;
        typeSpecBuilder.addField(parameterClassName, "mTarget", Modifier.PRIVATE);
        return typeSpecBuilder;
    }

    /**
     * 生成构造函数
     *
     * @param parameterClassName
     * @return
     */
    private MethodSpec.Builder makeConstructor(ClassName parameterClassName) {
        /* 生成 如下代码
              @UiThread
              public XXX_ViewBinding(MainActivity target) {
                this.mTarget = target;
              }
         */

        ClassName uiThreadClassName = ClassName.get("android.support.annotation", "UiThread");
        MethodSpec.Builder constructorBuilder = MethodSpec.constructorBuilder()
                .addAnnotation(uiThreadClassName)
                .addModifiers(Modifier.PUBLIC)
                .addParameter(parameterClassName, "target");
        // 构造函数中添加 this.target = target;
        constructorBuilder.addStatement("this.mTarget = target");
        return constructorBuilder;
    }

    /**
     * 生成unbind方法
     *
     * @param parameterClassName
     * @return
     */
    private MethodSpec.Builder makeUnbindMethod(ClassName parameterClassName) {
        /* 生成 如下代码
            @Override
            @CallSuper
            public void unbind() {
                MainActivity target = this.target;
                if (target == null) throw new IllegalStateException("Bindings already cleared.");
                this.target = null;
            }
        */
        ClassName callSuperClassName = ClassName.get("android.support.annotation", "CallSuper");
        MethodSpec.Builder unbindMethodBuilder = MethodSpec.methodBuilder("unbind")
                .addAnnotation(Override.class)
                .addAnnotation(callSuperClassName)
                .addModifiers(Modifier.PUBLIC)
                .returns(TypeName.VOID);
        // 在方法中添加代码
        unbindMethodBuilder.addStatement("$T target = this.mTarget", parameterClassName);
        unbindMethodBuilder.addStatement("if (target == null) return");
        unbindMethodBuilder.addStatement("this.mTarget = null");
        return unbindMethodBuilder;
    }

    // 用来指定支持的 SourceVersion
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    /**
     * 用来指定支持的 AnnotationTypes
     *
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        for (Class<? extends Annotation> annotation : getSupportedAnnotations()) {
            types.add(annotation.getCanonicalName());
        }
        return types;
    }

    /**
     * 参考了 ButterKnife 的写法
     *
     * @return
     */
    private Set<Class<? extends Annotation>> getSupportedAnnotations() {
        Set<Class<? extends Annotation>> annotations = new LinkedHashSet<>();
        // 我们要解析的自定义注解类，以后要添加Click事件也是在这里添加
        annotations.add(BindView.class);
        annotations.add(BindClick.class);
        return annotations;
    }

}
