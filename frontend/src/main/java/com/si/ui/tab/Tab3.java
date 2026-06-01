package com.si.ui.tab;

import com.si.MainView;
import com.si.config.ApplicationContext;
import com.si.servis.MathResponse;
import com.si.servis.WebService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MemoryBuffer;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.StreamResource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
@CssImport("./styles/tab3.css")
@Route(value = "/tab3", layout = MainView.class)
public class Tab3 extends VerticalLayout {
    Image imageMath = new Image();
    Button buttonMath = new Button("Sprawdź zadanie z zdjęcia zgodnie z tekstem");
    Button buttonMathOnlyText = new Button("Sprawdź zadanie z tekstu");
    Button buttonMathOnlyImage = new Button("Sprawdź zadanie z zdjęcia");
    MemoryBuffer memoryBufferMath = new MemoryBuffer();
    TextField textFieldMath = new TextField();

    public Tab3() {

        addClassName("tab3");
        try {
            buildUI();
        } catch (Exception e) {
            System.out.println("[Tab3] Error while building UI.");
        }
    }

    private void buildUI() {

        HorizontalLayout mainLayout = new HorizontalLayout();
        VerticalLayout content = new VerticalLayout();
        content.addClassName("content-tab3");
        Span description = new Span("Prześlij zdjecie: ");

        Upload upload = new Upload(memoryBufferMath);
        upload.setAcceptedFileTypes("image/png", "image/jpeg");

        HorizontalLayout layout = new HorizontalLayout();

        buttonMath.addClickListener(event -> {
            try {
                String number = textFieldMath.getValue();
                byte[] image = memoryBufferMath.getInputStream().readAllBytes();
                WebService webService = ApplicationContext.getBean(WebService.class);
                MathResponse result = webService.isValidMath(number, image,"both");

                String message;
                if (result.isFound()) {
                    message = result.getAnswer().toString();
                } else {
                    message = "To nie jest spójne zadanie matematyczne";
                }
                Notification notification = new Notification(message, 15000);
                notification.open();
            } catch (Exception e) {

            }
        });
        buttonMathOnlyText.addClickListener(event -> {
            try {
                String number = textFieldMath.getValue();
                byte[] image = memoryBufferMath.getInputStream().readAllBytes();
                WebService webService = ApplicationContext.getBean(WebService.class);
                MathResponse result = webService.isValidMath(number, image,"text");

                String message;
                if (result.isFound()) {
                    message = result.getAnswer().toString();
                } else {
                    message = "To nie jest spójne zadanie matematyczne";
                }
                Notification notification = new Notification(message, 15000);
                notification.open();
            } catch (Exception e) {

            }
        });
        buttonMathOnlyImage.addClickListener(event -> {
            try {
                String number = textFieldMath.getValue();
                byte[] image = memoryBufferMath.getInputStream().readAllBytes();
                WebService webService = ApplicationContext.getBean(WebService.class);
                MathResponse result = webService.isValidMath(number, image,"image");

                String message;
                if (result.isFound()) {
                    message = result.getAnswer().toString();
                } else {
                    message = "To nie jest spójne zadanie matematyczne";
                }
                Notification notification = new Notification(message, 15000);
                notification.open();
            } catch (Exception e) {

            }
        });
        textFieldMath.addValueChangeListener(e -> {
            enabledButton();
        });
        upload.addSucceededListener(e -> {

            try {
                byte[] bytes = memoryBufferMath.getInputStream().readAllBytes();
                StreamResource resource = new StreamResource(
                        memoryBufferMath.getFileName(),
                        () -> new ByteArrayInputStream(bytes)
                );
                imageMath.setSrc(resource);
                imageMath.setHeight("550px");
                imageMath.setWidth("550px");
                enabledButton();
            } catch (IOException ex) {

            }
        });
        upload.addFileRemovedListener(event->{

            memoryBufferMath = new MemoryBuffer();
            upload.setReceiver(memoryBufferMath);
            enabledButton();
        });
        buttonMath.setEnabled(false);
        buttonMathOnlyText.setEnabled(false);
        buttonMathOnlyImage.setEnabled(false);
        layout.add(textFieldMath, buttonMath,buttonMathOnlyText,buttonMathOnlyImage);

        content.add(description, upload, layout);
        mainLayout.add(content, imageMath);
        add(mainLayout);
    }

    private void enabledButton() {

        buttonMath.setEnabled(!textFieldMath.isEmpty() && memoryBufferMath.getFileData() != null);
        buttonMathOnlyImage.setEnabled(memoryBufferMath.getFileData() != null);
        buttonMathOnlyText.setEnabled(!textFieldMath.isEmpty());
    }
}
