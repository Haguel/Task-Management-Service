import { Component } from '@angular/core';
import {CdkDragDrop, moveItemInArray, transferArrayItem} from '@angular/cdk/drag-drop';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrl: './dashboard.component.css'
})
export class DashboardComponent {
  status_columns_list = {
    "Assigned": [
      'Get to work',
      'Pick up groceries',
      'Go home',
      'Fall asleep'
    ],
    "In Progress": [],
    "In Review": [],
    "Completed": [
      'Get up',
      'Brush teeth',
      'Take a shower',
      'Check e-mail',
      'Walk dog'
    ],
    "Deferred": [
      'Pol',
    ]
  }

  getClass(key: string): string {
    switch (key) {
      case "Assigned":
        return 'assigned-circle';
      case 'In Progress':
        return 'progress-circle';
      case 'In Review':
        return 'review-circle';
      case 'Completed':
        return 'complete-circle';
      case 'Deferred':
        return 'deferred-circle';
      default:
        return '';
    }
  }

  drop(event: CdkDragDrop<string[]>) {
    if (event.previousContainer === event.container) {
      moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
    } else {
      transferArrayItem(event.previousContainer.data,
        event.container.data,
        event.previousIndex,
        event.currentIndex);
    }
  }
}
