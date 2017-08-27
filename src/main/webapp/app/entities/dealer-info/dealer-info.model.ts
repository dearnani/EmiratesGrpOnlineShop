import { BaseEntity } from './../../shared';

const enum DealerStatus {
    'ACIVE',
    'INACTIVE',
    'BLOKED'
}

export class DealerInfo implements BaseEntity {
    constructor(
        public id?: number,
        public dealerCode?: string,
        public dealerName?: string,
        public dearLocation?: string,
        public dealerStatus?: DealerStatus,
        public productInfo?: BaseEntity,
        public location?: BaseEntity,
    ) {
    }
}
