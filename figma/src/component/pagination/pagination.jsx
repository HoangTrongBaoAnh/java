import React from 'react'

const Pagination = ({totalPage,currenPage, setCurrentPage}) => {
    const pageNumbers = [];

  for (let i = 1; i <= totalPage; i++) {
    pageNumbers.push(i);
  }
  return (
    <nav>
        <ul className='pagination'>
            {pageNumbers.map(item => (
                <li style={{margin:`5px`}} key={item} onClick={e => setCurrentPage(item-1)}>
                    {item}
                </li>
            ))}
        </ul>
    </nav>
  )
}

export default Pagination