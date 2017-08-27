import { BaseEntity } from './../../shared';

const enum PaymentChannel {
    'CASHON_DELIVERY',
    'ONLINE_CREDITCARD',
    'ONLINE_DEBITCARD'
}

const enum PaymentStatus {
    'SUCCESS',
    'FAIL'
}

export class PaymentDetails implements BaseEntity {
    constructor(
        public id?: number,
        public paymentMode?: PaymentChannel,
        public paymentStatus?: PaymentStatus,
        public oderNumber?: string,
        public oderDate?: any,
        public numberOfProducts?: number,
        public totalAmount?: number,
        public paymentDetails?: BaseEntity[],
    ) {
    }
}
