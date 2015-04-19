package com.freetmp.mbg.merge.parameter;

import com.freetmp.mbg.merge.AbstractMerger;
import com.github.javaparser.ast.TypeParameter;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by pin on 2015/4/12.
 */
public class TypeParameterMerger extends AbstractMerger<TypeParameter> {

    static {
        if(getMerger(TypeParameter.class) == null){
            register(TypeParameter.class,new TypeParameterMerger());
        }
    }

    private TypeParameterMerger(){}

    @Override
    public TypeParameter merge(TypeParameter first, TypeParameter second) {

        TypeParameter tp = new TypeParameter();
        tp.setName(first.getName());
        tp.setComment(mergeSingle(first.getComment(), second.getComment()));
        tp.setTypeBound(first.getTypeBound());
        tp.setAnnotations(mergeCollections(first.getAnnotations(), second.getAnnotations()));

        return tp;
    }

    @Override
    public boolean isEquals(TypeParameter first, TypeParameter second) {

        if(first == second) return true;

        if(!isAllNotNull(first,second)) return false;

        if(!StringUtils.equals(first.getName(),second.getName())) return false;

        List<ClassOrInterfaceType> firstTypeBounds = first.getTypeBound();
        List<ClassOrInterfaceType> secondTypeBounds = second.getTypeBound();

        if(firstTypeBounds == null) return secondTypeBounds == null;
        if(secondTypeBounds == null) return false;

        if(firstTypeBounds.size() != secondTypeBounds.size()) return false;

        AbstractMerger<ClassOrInterfaceType> merger = getMerger(ClassOrInterfaceType.class);

        for(int i = 0; i < firstTypeBounds.size(); i++){
            if(!merger.isEquals(firstTypeBounds.get(i),secondTypeBounds.get(i))){
                return false;
            }
        }

        return true;
    }
}
