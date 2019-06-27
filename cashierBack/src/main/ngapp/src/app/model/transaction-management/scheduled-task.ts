import { Transaction } from './transaction';
import { Tag } from '../hashtag-system/tag';

export interface ScheduledTask {
    id:string;
    userID:string;
    cronTab:string;
    amount:Number;
    tags:Tag[];
    title:string;
    ingestion:boolean;
}
