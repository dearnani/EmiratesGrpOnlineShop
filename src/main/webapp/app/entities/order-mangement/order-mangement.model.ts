import { BaseEntity } from './../../shared';

const enum OrderStatus {
    'SAVE',
    'SUCESS',
    'DELIVERY_FAIL',
    'PAYMENT_FAIL',
    'COMPLETE'
}

const enum PaymentStatus {
    'SUCCESS',
    'FAIL'
}

export class OrderMangement implements BaseEntity {
    constructor(
        public id?: number,
        public orderId?: number,
        public productCode?: number,
        public dealerCode?: string,
        public assignedPrice?: number,
        public dearLocation?: string,
        public orderStatus?: OrderStatus,
        public paymentStatus?: PaymentStatus,
        public productInfo?: BaseEntity,
        public paymentDetails?: BaseEntity,
        public customerInfo?: BaseEntity,
    ) {
    }
}
