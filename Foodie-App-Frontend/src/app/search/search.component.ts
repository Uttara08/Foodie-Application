import { Component, EventEmitter, Output } from '@angular/core';

@Component({
  selector: 'app-search',
  templateUrl: './search.component.html',
  styleUrl: './search.component.css'
})
export class SearchComponent {
  searchText: string = '';
  @Output()
  searchedText: EventEmitter<string> = new EventEmitter<string>();

  search() {
    this.searchedText.emit(this.searchText);
    this.searchText = '';
  }
}
