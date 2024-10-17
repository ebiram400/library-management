package ir.dotin.softwaresystems.librarymanagement.dto;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
public class VolumeInfo {
    private String title;
    private String subtitle;
    private List<String> authors;
    private String publishedDate;
    private String pageCount;
    private String category;
    private ImageLinks imageLinks;
    private String status="in google books";

    public VolumeInfo() {

    }
}
