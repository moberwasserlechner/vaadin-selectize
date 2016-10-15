package com.byteowls.vaadin.selectize.utils;

import java.io.Serializable;

public interface LabelGenerator<T> extends Serializable {

    String getLabel(T option);

}
