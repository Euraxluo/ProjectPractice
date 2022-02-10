# -*- coding: utf-8 -*- 
# Time: 2021-12-24 15:25
# Copyright (c) 2021
# author: Euraxluo

# !/usr/bin/env python
# author: Euraxluo
# encoding: utf-8
import asyncio
from solana.rpc.async_api import AsyncClient
from solana.account import *


async def main():
    async with AsyncClient("https://api.devnet.solana.com") as client:
        res = await client.is_connected()
    print(res)  # True

    # Alternatively, close the client explicitly instead of using a context manager:
    client = AsyncClient("https://api.devnet.solana.com")
    # res = await client.is_connected()
    # res = await client.get_version()
    res = await client.get_balance("8Lx9U9wdE3afdqih1mCAXy3unJDfzSaXFqAvoLMjhwoD","recent")
    print(res)  # True
    await client.close()


def new_account():
    x = Account()  # 新钱包
    print(x.__dict__)
    print(x.public_key().to_base58())  # 钱包地址
    print(x.public_key())  # 私钥

# def from_account():

if __name__ == '__main__':
    asyncio.run(main())
    # new_account()
    # from_account
