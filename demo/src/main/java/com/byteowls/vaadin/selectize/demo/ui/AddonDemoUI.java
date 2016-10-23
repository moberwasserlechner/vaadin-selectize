package com.byteowls.vaadin.selectize.demo.ui;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import com.byteowls.vaadin.selectize.demo.ui.views.AddonView;
import com.byteowls.vaadin.selectize.demo.ui.views.annotation.AnnotatedClassView;
import com.byteowls.vaadin.selectize.demo.ui.views.annotation.AnnotatedMultiCompView;
import com.byteowls.vaadin.selectize.demo.ui.views.annotation.UpdateItemsView;
import com.byteowls.vaadin.selectize.demo.ui.views.annotation.UpdateOptionsView;
import com.byteowls.vaadin.selectize.demo.ui.views.annotation.ValueChangeListenerView;
import com.byteowls.vaadin.selectize.demo.ui.views.annotation.FormLayoutLightView;
import com.byteowls.vaadin.selectize.demo.ui.views.annotation.FormLayoutView;
import com.byteowls.vaadin.selectize.demo.ui.views.contacts.MultipleCountryView;
import com.byteowls.vaadin.selectize.demo.ui.views.contacts.SingleContactView;
import com.byteowls.vaadin.selectize.demo.ui.views.contacts.SingleCountryView;
import com.vaadin.annotations.Theme;
import com.vaadin.data.Item;
import com.vaadin.data.util.HierarchicalContainer;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.Page;
import com.vaadin.server.Resource;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Tree;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import de.java2html.converter.JavaSource2HTMLConverter;
import de.java2html.javasource.JavaSource;
import de.java2html.javasource.JavaSourceParser;
import de.java2html.options.JavaSourceConversionOptions;
import de.java2html.util.IllegalConfigurationException;

@Theme("selectize")
@SpringUI
public class AddonDemoUI extends UI {

    private static final long serialVersionUID = -33887281222947647L;
    
    private static final String CAPTION_PROPERTY = "caption";
    private static final String ICON_PROPERTY = "icon";

    private static List<MenuItem> menuItems;
    static {
        menuItems = new ArrayList<>();
        menuItems.add(new MenuItem(MenuStructure.SINGLE, "Contact", SingleContactView.class));
        menuItems.add(new MenuItem(MenuStructure.SINGLE, "Country", SingleCountryView.class));
        menuItems.add(new MenuItem(MenuStructure.MULTIPLE, "Country", MultipleCountryView.class));
        menuItems.add(new MenuItem(MenuStructure.MULTIPLE, "Annotated", AnnotatedClassView.class));
        menuItems.add(new MenuItem(MenuStructure.MULTIPLE, "Multiple components", AnnotatedMultiCompView.class));
        menuItems.add(new MenuItem(MenuStructure.MULTIPLE, "Update/clear options", UpdateOptionsView.class));
        menuItems.add(new MenuItem(MenuStructure.MULTIPLE, "Update/clear items/selected", UpdateItemsView.class));
        menuItems.add(new MenuItem(MenuStructure.MULTIPLE, "On Blur", ValueChangeListenerView.class));
        
        menuItems.add(new MenuItem(MenuStructure.FORM, "Form", FormLayoutView.class));
        menuItems.add(new MenuItem(MenuStructure.FORM, "Light form", FormLayoutLightView.class));
    }

    @Autowired
    private SpringViewProvider viewProvider;
    @Autowired
    private Environment env;
    
    private Label codeLabel;
    private Panel editorComponent;
    
    private Navigator navigator;

    @SuppressWarnings("serial")
    @Override
    protected void init(VaadinRequest request) {
        String title = env.getProperty("addon.title");
        getPage().setTitle(title);
        Responsive.makeResponsive(this);
        
        VerticalLayout vl = new VerticalLayout();
        vl.setSizeFull();
        
        Label info = new Label("<strong>" + title + "</strong> "
                + "| Version: <strong>" + env.getProperty("addon.version") + "</strong> "
                + "| "+env.getProperty("addon.jslib.title")+": <strong>" + env.getProperty("addon.jslib.version") + "</strong> "
                + "| Vaadin: <strong>" + env.getProperty("addon.vaadin.version") + "</strong> "
                + "| <a href=\""+env.getProperty("addon.github")+"\">Check it out on Github</a>");
        info.setContentMode(ContentMode.HTML);
        
        CssLayout infoBar = new CssLayout(info);
        infoBar.setWidth(100, Unit.PERCENTAGE);
        infoBar.addStyleName("addon-info-bar");
        vl.addComponent(infoBar);

        HorizontalSplitPanel splitContentCode = new HorizontalSplitPanel();
        splitContentCode.setSizeFull();
        splitContentCode.setFirstComponent(buildContent());
        splitContentCode.setSecondComponent(buildCode());
        splitContentCode.setSplitPosition(50);

        HorizontalSplitPanel splitMenuContent = new HorizontalSplitPanel();
        splitMenuContent.setSizeFull();
        splitMenuContent.setFirstComponent(buildMenuTree());
        splitMenuContent.setSecondComponent(splitContentCode);
        splitMenuContent.setSplitPosition(15);
        vl.addComponent(splitMenuContent);
        vl.setExpandRatio(splitMenuContent, 1);
        

        navigator = new Navigator(this, editorComponent);
        navigator.addProvider(viewProvider);
        navigator.setErrorProvider(viewProvider);
        navigator.addViewChangeListener(new ViewChangeListener() {
            @Override
            public boolean beforeViewChange(ViewChangeEvent event) {
                AddonView view = (AddonView) event.getNewView();
                String formattedSourceCode = getFormattedSourceCode(view.getSource());
                codeLabel.setValue(formattedSourceCode);
                return true;
            }

            @Override
            public void afterViewChange(ViewChangeEvent event) {

            }
        });
        setContent(vl);

        String fragment = Page.getCurrent().getUriFragment();
        if (fragment == null || fragment.equals("")) {
            String viewName = menuItems.get(0).getViewName();
            navigator.navigateTo(viewName);
        }
    }

    private Component buildContent() {
        editorComponent = new Panel();
        editorComponent.setSizeFull();
        editorComponent.addStyleName(ValoTheme.PANEL_BORDERLESS);
        return editorComponent;
    }
    
    private Component buildCode() {
        codeLabel = new Label();
        codeLabel.setContentMode(ContentMode.HTML);
        
        Panel codePanel = new Panel(codeLabel);
        codePanel.setSizeFull();
        codePanel.addStyleName(ValoTheme.PANEL_BORDERLESS);
        codePanel.addStyleName("addon-code");
        return codePanel;
    }

    @SuppressWarnings("unchecked")
    private Component buildMenuTree() {
        Panel treePanel = new Panel();
        treePanel.setSizeFull();
        treePanel.addStyleName(ValoTheme.PANEL_BORDERLESS);
        treePanel.addStyleName("addon-menu");
        
        Tree tree = new Tree();
        tree.setSelectable(true);

        HierarchicalContainer treeContainer = new HierarchicalContainer();
        treeContainer.addContainerProperty(CAPTION_PROPERTY, String.class, null); // label
        treeContainer.addContainerProperty(ICON_PROPERTY, Resource.class, null); // icon

        tree.setContainerDataSource(treeContainer);
        tree.setItemCaptionPropertyId(CAPTION_PROPERTY);
        tree.setItemIconPropertyId(ICON_PROPERTY);

        for (MenuStructure editorStructure : MenuStructure.values()) {
            List<MenuItem> children = new ArrayList<>();
            for (MenuItem i : menuItems) {
                if (i.getType() == editorStructure) {
                    children.add(i);
                }
            }

            Item item = treeContainer.addItem(editorStructure);
            item.getItemProperty(CAPTION_PROPERTY).setValue(editorStructure.toString());
            item.getItemProperty(ICON_PROPERTY).setValue(editorStructure.getIcon());
            treeContainer.setChildrenAllowed(editorStructure, !children.isEmpty());

            for (MenuItem i : children) {
                Item childItem = treeContainer.addItem(i);
                childItem.getItemProperty(CAPTION_PROPERTY).setValue(i.getLabel());
                //childItem.getItemProperty(ICON_PROPERTY).setValue(null);
                treeContainer.setParent(i, editorStructure);
                treeContainer.setChildrenAllowed(i, false);
            }
        }

        // Expand whole tree
        for (final Object id : tree.rootItemIds()) {
            tree.expandItem(id);
        }

        tree.addItemClickListener(e -> {
            Object itemId = e.getItemId();
            if (itemId instanceof MenuItem) {
                MenuItem menuItem = (MenuItem) itemId;
                if (menuItem.getViewName() != null) {
                    getUI().getNavigator().navigateTo(menuItem.getViewName());
                }

            }
        });
        treePanel.setContent(tree);
        return treePanel;
    }

    public String getFormattedSourceCode(String sourceCode) {
        if (sourceCode != null) {
            try {
                JavaSource source = new JavaSourceParser().parse(new StringReader(sourceCode));
                JavaSource2HTMLConverter converter = new JavaSource2HTMLConverter();
                StringWriter writer = new StringWriter();
                JavaSourceConversionOptions options = JavaSourceConversionOptions.getDefault();
                options.setShowLineNumbers(false);
                options.setAddLineAnchors(false);
                converter.convert(source, options, writer);
                return writer.toString();
            } catch (IllegalConfigurationException | IOException exception) {

            }
        }
        return sourceCode;
    }

}
