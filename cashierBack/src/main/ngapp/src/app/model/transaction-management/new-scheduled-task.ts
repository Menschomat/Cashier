import { Transaction } from './transaction';
import { Tag } from '../hashtag-system/tag';
import { ScheduledTask } from './scheduled-task';


export interface NewScheduledTask {
    task:ScheduledTask;
    tags:Tag[];
}
