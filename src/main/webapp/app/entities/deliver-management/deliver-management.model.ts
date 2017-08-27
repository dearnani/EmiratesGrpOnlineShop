import { BaseEntity } from './../../shared';

const enum DeliveryStatus {
    'DISPATCHED',
    'TRANSIT',
    'RETURNED',
    'DOORLOCKED',
    'DELIVERED',
    'UNDELIVERED'
}

export class DeliverManagement implements BaseEntity {
    constructor(
        public id?: number,
        public orderNumber?: number,
        public deliveryStatus?: DeliveryStatus,
        public location?: BaseEntity,
    ) {
    }
}
