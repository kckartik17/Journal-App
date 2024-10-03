package com.kckartik.journalApp.controller;

import com.kckartik.journalApp.entity.JournalEntry;
import com.kckartik.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping
    public List<JournalEntry> getAll() {
        return journalEntryService.getAll();
    }

    @PostMapping
    public JournalEntry createEntry(@RequestBody JournalEntry journalEntry){
        journalEntry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(journalEntry);
        return journalEntry;
    }

    @GetMapping("/{id}")
    public JournalEntry getJournalEntryById(@PathVariable ObjectId id) {
        return journalEntryService.findById(id).orElse(null);
    }

    @DeleteMapping("/{id}")
    public boolean deleteJournalEntryById(@PathVariable ObjectId id) {
        journalEntryService.deleteById(id);
        return true;
    }

    @PutMapping("/{id}")
    public JournalEntry updateJournalEntryById(@PathVariable ObjectId id, @RequestBody JournalEntry journalEntry) {
        JournalEntry oldJournal = journalEntryService.findById(id).orElse(null);
        if(oldJournal != null) {
            oldJournal.setTitle(!journalEntry.getTitle().isEmpty() ? journalEntry.getTitle() : oldJournal.getTitle());
            oldJournal.setContent(!journalEntry.getContent().isEmpty() ? journalEntry.getContent() : oldJournal.getContent());
        }
        journalEntryService.saveEntry(oldJournal);
        return oldJournal;
    }
}
